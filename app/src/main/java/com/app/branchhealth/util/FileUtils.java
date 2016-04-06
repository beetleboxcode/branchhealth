package com.app.branchhealth.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FileUtils{
	public static final String THEMES_FOLDER = "themes";

	public static File createImageFilePath(String filename, String folder, String packageName){
		File path = new File(Environment.getExternalStorageDirectory(), packageName);
		path = new File(path, folder);
		if(!path.exists())
			path.mkdirs();
		return new File(path, filename + ".jpg");
	}

	public static boolean isFileExist(String path){
		File file = new File(path);
		return file.exists();
	}

	public static boolean isFileExistInFolder(String filename, String folder, String packageName){
		File file = new File(Environment.getExternalStorageDirectory(), packageName);
		file = new File(file, folder);
		file = new File(file, filename);
		return file.exists();
	}

	public static boolean isFileEquals(String filename, String folder, String packageName, long fileLength){
		File file = new File(Environment.getExternalStorageDirectory(), packageName);
		file = new File(file, folder);
		file = new File(file, filename);
		boolean isEqual = false;
		if(file.exists() && file.length() > 0){
			//      isEqual = file.length() == fileLength;
			isEqual = true;
		}
		return isEqual;
	}

	public static String getFilePath(String filename, String folder, String packageName){
		File path = new File(Environment.getExternalStorageDirectory(), packageName);
		path = new File(path, folder);
		if(!path.exists())
			return null;
		else{
			path = new File(path, filename + ".jpg");
			if(path.exists()){
				return path.getAbsolutePath();
			}else
				return null;
		}
	}

	public static String getPath(Activity activity, Uri uri){
		String[] projection = {MediaColumns.DATA};
		Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public static boolean copyFile(File source, File destination){
		boolean isOk = false;

		try{
			if(source.exists()){
				@SuppressWarnings("resource")
				FileChannel src = new FileInputStream(source).getChannel();
				@SuppressWarnings("resource")
				FileChannel dst = new FileOutputStream(destination).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();
			}
			isOk = true;
			Log.i(FileUtils.class.getName(), "copyFile src: " + source + ", dst: " + destination + " --- SUCCESS ---");
		}catch(Exception e){
			Log.e(FileUtils.class.getName(), "copyFile src: " + source + ", dst: " + destination + " --- FAILED ---\n" + e.getMessage());
		}
		return isOk;
	}

	/**
	 * Gets the last image path from the media store
	 *
	 * @return
	 */
	public static String getLastImagePath(Activity activity){
		final String[] imageColumns = {BaseColumns._ID, MediaColumns.DATA};
		final String imageOrderBy = BaseColumns._ID + " DESC";
		Cursor imageCursor = activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
		if(imageCursor.moveToFirst()){
			String fullPath = imageCursor.getString(imageCursor.getColumnIndex(MediaColumns.DATA));
			Log.d(FileUtils.class.getName(), "getLastImageId::path " + fullPath);
			imageCursor.close();
			return fullPath;
		}else{
			return null;
		}
	}

	/**
	 * Gets the last image id from the media store
	 *
	 * @return
	 */
	public static int getLastImageId(Activity activity){
		final String[] imageColumns = {BaseColumns._ID, MediaColumns.DATA};
		final String imageOrderBy = BaseColumns._ID + " DESC";
		Cursor imageCursor = activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
		if(imageCursor.moveToFirst()){
			int id = imageCursor.getInt(imageCursor.getColumnIndex(BaseColumns._ID));
			Log.d(FileUtils.class.getName(), "getLastImageId::id " + id);
			imageCursor.close();
			return id;
		}else{
			return 0;
		}
	}

	public static void CopyStream(InputStream is, OutputStream os){
		final int buffer_size = 1024;
		try{
			byte[] bytes = new byte[buffer_size];
			for(;;){
				int count = is.read(bytes, 0, buffer_size);
				if(count == -1)
					break;
				os.write(bytes, 0, count);
			}
		}catch(Exception ex){
		}
	}

	public static File createFile(String filename, String folder, String packageName){
		File path = new File(Environment.getExternalStorageDirectory(), packageName);
		path = new File(path, folder);
		if(!path.exists())
			path.mkdirs();
		return new File(path, filename);
	}

	@TargetApi(8)
	public static String convertByteToImage(String filename, String packageName, ArrayList<String> sImgBase64s) throws FileNotFoundException, IOException{
		ArrayList<byte[]> bImgs = new ArrayList<byte[]>();

		int i = 0, newLength = 0;
		for(String sImgBase64 : sImgBase64s){
			bImgs.add(Base64.decode(sImgBase64, Base64.DEFAULT));
			newLength += bImgs.get(i).length;
			i++;
		}

		byte[] byteImage = new byte[newLength];

		int byteStart = 0;
		for(byte[] bImg : bImgs){
			System.arraycopy(bImg, 0, byteImage, byteStart, bImg.length);
			byteStart += bImg.length;
		}

		Bitmap decodeByte = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
		if(decodeByte == null) throw new RuntimeException("Couldn't create bitmap from bytes");
		File imageFile = createFile(filename, THEMES_FOLDER, packageName);
		imageFile.createNewFile();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		decodeByte.compress(CompressFormat.PNG, 0, bos);
		byte[] bitmapData = bos.toByteArray();
		@SuppressWarnings("resource")
		FileOutputStream fos = new FileOutputStream(imageFile);
		fos.write(bitmapData);

		return imageFile.getAbsolutePath();
	}

	public static void createFileFromBitmap(Bitmap bitmap, File destinationFilePath){
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, bytes);

		// write the bytes in file
		FileOutputStream fo = null;
		try{
			fo = new FileOutputStream(destinationFilePath);
			fo.write(bytes.toByteArray());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				bytes.flush();
				if(fo != null)
					fo.flush();
			}catch(Exception e){

			}
		}
	}
}
