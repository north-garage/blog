package io.north.garage.blog.service;

import com.amazonaws.services.s3.model.S3Object;
import io.north.garage.blog.configuration.properties.AwsS3Properties;
import io.north.garage.blog.dto.BlogImageDto;
import io.north.garage.blog.exception.ApplicationSystemException;
import io.north.garage.blog.repository.BlogImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BlogImageService {
    @Autowired
    private BlogImageRepository blogImageRepository;

    @Autowired
    private AwsS3Properties properties;

    private static final String IMAGE_DIRECTORY_PATH = "image/blog/";

    private static final String UPLOAD_EXTENSION = "jpg";

    /**
     * 記事の画像イメージをアップロードします.
     *
     * @param is 画像ストリーム
     * @return dto
     */
    public BlogImageDto upload(final InputStream is) throws IOException {
        final BlogImageDto dto = createBlogImageDto(UUID.randomUUID().toString());

        try (final InputStream stream = convertImageFormat(is, UPLOAD_EXTENSION)) {
            blogImageRepository.putObject(
                properties.getBucketName(), dto.getImagePath(), stream);
        }
        return dto;
    }

    /**
     * 記事の画像イメージを取得します.
     *
     * @param fileName 検索ファイル名
     * @param function 書き込みfunction
     * @throws IOException
     */
    public void fetchImage(final String fileName,
                           final BlogImageFunction function) throws IOException {
        final BlogImageDto dto = createBlogImageDto(fileName);

        final S3Object s3Object = blogImageRepository.getObject(
            properties.getBucketName(), dto.getImagePath());

        function.writeImage(s3Object.getObjectContent());
    }

    /**
     * 記事の画像イメージを指定の形式に変換します.
     *
     * @param is         対象のストリーム
     * @param formatName ファイル形式
     * @return 変換後のストリーム
     */
    private InputStream convertImageFormat(final InputStream is,
                                           final String formatName) {
        Assert.notNull(is, "InputStream must be non-null.");
        Assert.notNull(formatName, "FormatName must be non-null.");

        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final BufferedImage tmp = ImageIO.read(is);

            if (tmp == null) {
                throw new ApplicationSystemException("Cannot convert to buffered-image.");
            }
            if (hasImageFormat(tmp, formatName)) {
                return is;
            }
            // pngを変換すると色がおかしくなる場合があるので, Graphicに描画してから変換する
            final BufferedImage image = new BufferedImage(
                tmp.getWidth(), tmp.getHeight(), BufferedImage.TYPE_INT_RGB);
            final Graphics graphics = image.getGraphics();
            graphics.drawImage(tmp, 0, 0, null);
            graphics.dispose();

            ImageIO.write(image, formatName, out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new ApplicationSystemException("Fail to read image.", e);
        }
    }

    /**
     * ストリームが対象の形式を持つかを返します.
     *
     * @param is         対象のストリーム
     * @param formatName ファイル形式
     * @return true: formatあり / false: formatなし
     * @throws IOException
     */
    private boolean hasImageFormat(final BufferedImage is,
                                   final String formatName) throws IOException {
        Assert.notNull(is, "InputStream must be non-null.");
        Assert.notNull(formatName, "Format name must be non-null.");

        final Iterator<ImageReader> readers = ImageIO.getImageReaders(is);

        while (readers.hasNext()) {
            final ImageReader reader = readers.next();

            if (reader.getFormatName().equals(formatName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param fileName ファイル名
     * @return dto
     */
    private static BlogImageDto createBlogImageDto(final String fileName) {

        return new BlogImageDto(IMAGE_DIRECTORY_PATH, fileName, UPLOAD_EXTENSION);
    }

    @FunctionalInterface
    public interface BlogImageFunction {
        void writeImage(final InputStream in) throws IOException;
    }
}