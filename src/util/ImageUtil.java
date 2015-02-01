package util;

import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.imageio.stream.*;

import com.sun.image.codec.jpeg.*;
import org.imgscalr.*;

public class ImageUtil {
	public ImageUtil() {
	}

	private static BufferedImage makeThumbnail(Image img, int width, int height) {
		BufferedImage tag = new BufferedImage(width, height, 1);
		Graphics g = tag.getGraphics();
		// g.drawImage(img.getScaledInstance(width, height, 4), 0, 0, null);
		g.drawImage(
				img.getScaledInstance(width, height, Image.SCALE_REPLICATE), 0,
				0, null);
		g.dispose();
		return tag;
	}

	private static BufferedImage resizeImageWithHint(
			BufferedImage originalImage, int width, int height, int type) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

	private static void saveSubImage(BufferedImage image,
			Rectangle subImageBounds, File subImageFile) throws IOException {
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		BufferedImage subImage = new BufferedImage(subImageBounds.width,
				subImageBounds.height, 1);
		Graphics g = subImage.getGraphics();
		if (subImageBounds.width > image.getWidth()
				|| subImageBounds.height > image.getHeight()) {
			System.out.println("### ImageUtil 111");
			int left = subImageBounds.x;
			int top = subImageBounds.y;
			if (image.getWidth() < subImageBounds.width) {
				left = (subImageBounds.width - image.getWidth()) / 2;
			}
			if (image.getHeight() < subImageBounds.height) {
				top = (subImageBounds.height - image.getHeight()) / 2;
			}
			g.setColor(Color.white);
			g.fillRect(0, 0, subImageBounds.width, subImageBounds.height);
			g.drawImage(image, left, top, null);
		} else {
			System.out
					.println("### image.width=" + image.getWidth() + " height="
							+ image.getHeight() + " subImageBounds.x="
							+ subImageBounds.x + " y=" + subImageBounds.y
							+ " width=" + subImageBounds.width + " height="
							+ subImageBounds.height);
			g.drawImage(image.getSubimage(subImageBounds.x, subImageBounds.y,
					subImageBounds.width, subImageBounds.height), 0, 0, null);
		}
		g.dispose();
		ImageIO.write(subImage, formatName, subImageFile);
	}

	public static void cut(String srcImageFile, String descFile, int width,
			int height, Rectangle rect) throws IOException {
		Image image = ImageIO.read(new File(srcImageFile));
		BufferedImage bImage = makeThumbnail(image, width, height);
		saveSubImage(bImage, rect, new File(descFile));
	}
	
	public static long resize(File imgFile, int width, int height)
	    throws Exception
	{
	    String fpath = imgFile.getAbsolutePath();
	    String ext = fpath.substring(fpath.lastIndexOf('.') + 1);
		
	    BufferedImage image = null;
	    if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
	    	Image img = null;
            ImageInputStream iis = new FileImageInputStream(imgFile);
            try {
                for (Iterator<ImageReader> i = ImageIO.getImageReaders(iis); img == null && i.hasNext();) {
                    ImageReader r = i.next();
                    try {
                        r.setInput(iis);
                        image = r.read(0);
                    } catch (IOException e) {}
                }
            } finally {
                iis.close();
            }
	    }
	    else
	    	image = ImageIO.read(imgFile);
	    
	    // BufferedImage bImage = makeThumbnail(image, width, height);
    	int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
    	// BufferedImage bImage = resizeImageWithHint(image, width, height, type);
		// BufferedImage bImage = getScaledInstance(image, width, height, Image.SCALE_DEFAULT, true);
    	
//File f1 = new File("/opt/resin/webapps/yvent_v3/1.jpg");    	
//ImageIO.write(image, ext, f1);    	
		BufferedImage bImage = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
				width, height, Scalr.OP_ANTIALIAS);
//File f2 = new File("/opt/resin/webapps/yvent_v3/2.jpg");    	
//ImageIO.write(bImage, ext, f2);

	    ImageIO.write(bImage, ext, imgFile);
	    return imgFile.length();
	}	
	
	public static void cut(File srcImageFile, File descFile, int width,
			int height, Rectangle rect) throws IOException {
		Image image = ImageIO.read(srcImageFile);
		BufferedImage bImage = makeThumbnail(image, width, height);
		saveSubImage(bImage, rect, descFile);
	}

	public static void cut2(File srcImageFile, File descFile, Dimension d,
			Rectangle rect) throws IOException {
		Image image = ImageIO.read(srcImageFile);
		BufferedImage bImage = makeThumbnail(image, (int) d.getWidth(), (int) d
				.getHeight());
		saveSubImage(bImage, rect, descFile);
	}

	public static Dimension getImageDim(final String path) {
		Dimension result = null;
		String suffix = getFileSuffix(path);
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
		if (iter.hasNext()) {
			ImageReader reader = iter.next();
			try {
				ImageInputStream stream = new FileImageInputStream(new File(
						path));
				reader.setInput(stream);
				int width = reader.getWidth(reader.getMinIndex());
				int height = reader.getHeight(reader.getMinIndex());
				result = new Dimension(width, height);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.dispose();
			}
		} else {
			System.out.println("No reader found for given format: " + suffix);
		}
		return result;
	}

	private static String getFileSuffix(final String path) {
		String result = null;
		if (path != null) {
			result = "";
			if (path.lastIndexOf('.') != -1) {
				result = path.substring(path.lastIndexOf('.'));
				if (result.startsWith(".")) {
					result = result.substring(1);
				}
			}
		}
		return result;
	}

	public static File get_folder(String subdir, int obj_id, String webroot)
			throws Exception {
		File root = new File(webroot);
		File root2 = new File(root, subdir);
		if (root2.exists() && !root2.isDirectory()) {
			throw new Exception(subdir + " cannot be created");
		}
		if (!root2.exists()) {
			root2.mkdir();
		}
		File ret = new File(root2, obj_id + "");
		if (ret.exists() && !root2.isDirectory()) {
			throw new Exception("folder cannot be created");
		}
		if (!ret.exists())
			ret.mkdir();
		return ret;
	}

	public static String get_unique_filename(File folder, String org_file_name)
			throws Exception {
		Random r = new Random(new Date().getTime());
		int c = org_file_name.lastIndexOf(".");
		String ext = (c > 0) ? org_file_name.substring(c + 1) : "";
		StringBuffer sb = null;
		String ret = null;
		int span = 'z' - 'A';
		while (true) {
			sb = new StringBuffer();
			for (int i = 0; i < 7;) {
				int t = ((int) 'A') + r.nextInt(span);
				if (t <= 96 && t >= 91)
					continue;
				i++;
				sb.append((char) t);
			}
			ret = sb.toString() + "." + ext;
			File newf = new File(folder, ret);
			if (!newf.exists())
				break;
		}
		return ret;
	}

	public static boolean checkImgExt(String fname) throws Exception {
		String n = fname.toLowerCase();
		if (n.indexOf("png") < 0 && n.indexOf("jpg") < 0
				&& n.indexOf("gif") < 0 && n.indexOf("jpeg") < 0
				&& n.indexOf("bmp") < 0) {
			return false;
		}
		return true;
	}

	public static Dimension ensureSize(File newf, int maxSide, int maxSize)
			throws Exception {
		Dimension dm = ImageUtil.getImageDim(newf.getAbsolutePath());
		int new_width = (int) dm.getWidth();
		int new_height = (int) dm.getHeight();
		if (maxSide > 0) {
			System.out.println("in ImgeUtil #1");
			if (dm.getWidth() > dm.getHeight() && dm.getHeight() > maxSide) {
				new_height = maxSide;
				new_width = (int) (((float) new_height / (float) dm.getHeight()) * (float) dm
						.getWidth());
				ImageUtil.resize(newf, new_width, new_height);
				System.out.println("in ImgeUtil #2");
			} else if (dm.getHeight() > dm.getWidth()
					&& dm.getWidth() > maxSide) {
				new_width = maxSide;
				new_height = (int) (((float) new_width / (float) dm.getWidth()) * (float) dm
						.getHeight());
				ImageUtil.resize(newf, new_width, new_height);
				System.out.println("in ImgeUtil #3");
			}
		}
		if (maxSize > 0) {
			long old_size = (int) newf.length();
			while (true) {
				System.out.println("in ImgeUtil #4");
				if (old_size <= maxSize)
					break;
				new_width = (int) (new_width * 4) / 5;
				new_height = (int) (new_height * 4) / 5;
				old_size = ImageUtil.resize(newf, new_width, new_height);
				System.out.println("in ImgeUtil #5");
			}
		}
		return ImageUtil.getImageDim(newf.getAbsolutePath());
	}

	public static Dimension ensureSizeMaxSide(File newf, int maxSide,
			int maxSize) throws Exception {
		Dimension dm = ImageUtil.getImageDim(newf.getAbsolutePath());
		int new_width = (int) dm.getWidth();
		int new_height = (int) dm.getHeight();
		if (maxSide > 0) {
			if (dm.getHeight() > dm.getWidth() && dm.getHeight() > maxSide) {
				new_height = maxSide;
				new_width = (int) (((float) new_height / (float) dm.getHeight()) * (float) dm
						.getWidth());
				ImageUtil.resize(newf, new_width, new_height);
			} else if (dm.getWidth() > maxSide) {
				new_width = maxSide;
				new_height = (int) (((float) new_width / (float) dm.getWidth()) * (float) dm
						.getHeight());
				ImageUtil.resize(newf, new_width, new_height);
			}
		}
		if (maxSize > 0) {
			while (true) {
				if (newf.length() <= maxSize)
					break;
				new_height = (int) (new_width * 3) / 4;
				new_width = (int) (new_height * 3) / 4;
				ImageUtil.resize(newf, new_width, new_height);
			}
		}
		return ImageUtil.getImageDim(newf.getAbsolutePath());
	}

//	public static String uploadYun(String dir, File newf, boolean remove_org)
//			throws Exception {
//		com.yvent.api.UpYun upyun = new com.yvent.api.UpYun("yimage", "www",
//				"57406531");
//		upyun.setContentMD5(upyun.md5(newf));
//		// upyun.setFileSecret("yv");
//		if (dir.charAt(0) != '/')
//			dir = "/" + dir;
//		if (dir.charAt(dir.length() - 1) != '/')
//			dir += "/";
//		String jpgpath = dir + newf.getName();
//		upyun.writeFile(jpgpath, newf, true);
//		String url = "http://yimage.b0.upaiyun.com" + dir + newf.getName();
//		if (remove_org) {
//			System.out.println("here deleting " + newf.getAbsolutePath());
//			newf.delete();
//		}
//		return url;
//	}

//	public static void deleteYun(String dir, String fname) throws Exception {
//		com.yvent.api.UpYun upyun = new com.yvent.api.UpYun("yimage", "www",
//				"57406531");
//		if (dir.charAt(0) != '/')
//			dir = "/" + dir;
//		if (dir.charAt(dir.length() - 1) != '/')
//			dir += "/";
//		String jpgpath = dir + fname;
//		upyun.deleteFile(jpgpath);
//	}

	public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth,
			int targetHeight, Object hint, boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			//g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}
}