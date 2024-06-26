import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEncryption {

    // Encrypt image by adding a key to each pixel value
    public static void encryptImage(String inputImagePath, String outputImagePath, int key) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputImagePath));

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Encrypting by adding the key to each pixel value
                r = (r + key) % 256;
                g = (g + key) % 256;
                b = (b + key) % 256;

                int encryptedRGB = (r << 16) | (g << 8) | b;
                image.setRGB(x, y, encryptedRGB);
            }
        }

        File encryptedImageFile = new File(outputImagePath);
        ImageIO.write(image, "png", encryptedImageFile);
    }

    // Decrypt image by subtracting the key from each pixel value
    public static void decryptImage(String encryptedImagePath, String decryptedImagePath, int key) throws IOException {
        BufferedImage encryptedImage = ImageIO.read(new File(encryptedImagePath));

        for (int y = 0; y < encryptedImage.getHeight(); y++) {
            for (int x = 0; x < encryptedImage.getWidth(); x++) {
                int rgb = encryptedImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Decrypting by subtracting the key from each pixel value
                r = (r - key + 256) % 256;
                g = (g - key + 256) % 256;
                b = (b - key + 256) % 256;

                int decryptedRGB = (r << 16) | (g << 8) | b;
                encryptedImage.setRGB(x, y, decryptedRGB);
            }
        }

        File decryptedImageFile = new File(decryptedImagePath);
        ImageIO.write(encryptedImage, "png", decryptedImageFile);
    }

    public static void main(String[] args) {
        try {
            String inputImagePath = "path_to_your_input_image.png";
            String encryptedImagePath = "path_to_save_encrypted_image.png";
            String decryptedImagePath = "path_to_save_decrypted_image.png";
            int key = 50; // Your encryption/decryption key

            encryptImage(inputImagePath, encryptedImagePath, key);
            System.out.println("Image encrypted and saved at: " + encryptedImagePath);

            decryptImage(encryptedImagePath, decryptedImagePath, key);
            System.out.println("Image decrypted and saved at: " + decryptedImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
