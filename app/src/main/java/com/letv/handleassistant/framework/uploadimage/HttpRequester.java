package com.letv.handleassistant.framework.uploadimage;


import com.letv.handleassistant.application.SoftApplication;
import com.letv.handleassistant.framework.network.Request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;



public class HttpRequester {
	private static int REQUEST_TIME_OUT = 60 * 1000;

	public static String post(Request request, FormFile formFile) {
//		String serverAddress = SoftApplication.softApplication.getAppInfo().serverAddress;
//		LogUtil.log("serverAddress:" + serverAddress);
//		String result = "";
//		String PREFIX = "--";
//		String LINEND = "\r\n";
//		String CHARSET = "UTF-8";
//		String MULTIPART_FROM_DATA = "multipart/form-data";
//		String BOUNDARY = java.util.UUID.randomUUID().toString();
//		try {
//			URL url = new URL(serverAddress
//					+ request.getServerInterfaceDefinition().getOpt());
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("POST");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setUseCaches(false);
//			conn.setConnectTimeout(REQUEST_TIME_OUT);
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("Charset", "UTF-8");
//			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
//					+ ";boundary=" + BOUNDARY);
//			// 首先组拼文本类型的参数
//			StringBuilder sb = new StringBuilder();
//			Map<String, String> params = request.getParamsMap();
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				if(StringUtil.isNullOrEmpty(entry.getValue())){
//					continue;
//				}
//				sb.append(PREFIX);
//				sb.append(BOUNDARY);
//				sb.append(LINEND);
//				sb.append("Content-Disposition: form-data; name=\""
//						+ entry.getKey() + "\"" + LINEND);
//				// 这里加上这句话 ，不知道服务端为什么会是乱码
//				// sb.append("Content-Type: text/plain; charset=" + CHARSET +
//				// LINEND);
//				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
//				sb.append(LINEND);
//				sb.append(entry.getValue());
//				sb.append(LINEND);
//
//				LogUtil.log("参数：" + entry.getKey() + "值：" + entry.getValue());
//			}
//			DataOutputStream outStream = new DataOutputStream(
//					conn.getOutputStream());
//			outStream.write(sb.toString().getBytes());
//
//			/**
//			 * 下面是传图
//			 */
//			InputStream inputStream = formFile.inputStreamValue;
//			if (inputStream != null) {
//				StringBuilder sb1 = new StringBuilder();
//				sb1.append(PREFIX);
//				sb1.append(BOUNDARY);
//				sb1.append(LINEND);
//				sb1.append("Content-Disposition: form-data; name=\""
//						+ formFile.inputStreamKey + "\"; filename=\""
//						+ formFile.fileName + "\"" + LINEND);
//				sb1.append("Content-Type: image/jpg; charset=" + CHARSET
//						+ LINEND);
//				sb1.append(LINEND);
//				outStream.write(sb1.toString().getBytes());
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				LogUtil.log("返回inputStream == null ? " + (inputStream == null));
//				while ((len = inputStream.read(buffer)) != -1) {
//					outStream.write(buffer, 0, len);
//				}
//
//				inputStream.close();
//				outStream.write(LINEND.getBytes());
//			}
//
//			// 请求结束标志
//			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//			outStream.write(end_data);
//			outStream.flush();
//			int responseCode = conn.getResponseCode();
//			if (responseCode != 200) {
//				LogUtil.log("返回responseCode：" + responseCode);
//				return null;
//			}
//			InputStream in = conn.getInputStream();
//			InputStreamReader isReader = new InputStreamReader(in, CHARSET);
//			BufferedReader bufReader = new BufferedReader(isReader);
//			result = bufReader.readLine();
//			LogUtil.log("返回result：" + result);
//			conn.disconnect();
//			return result;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return null;
	}

	public static String postArray(Request request, FormFile formFile) {
//		String serverAddress = SoftApplication.softApplication.getAppInfo().serverAddress;
//
//		String result = "";
//		String PREFIX = "--";
//		String LINEND = "\r\n";
//		String CHARSET = "UTF-8";
//		String MULTIPART_FROM_DATA = "multipart/form-data";
//		String BOUNDARY = java.util.UUID.randomUUID().toString();
//		try {
//			URL url = new URL(serverAddress
//					+ request.getServerInterfaceDefinition().getOpt());
//			LogUtil.log("请求地址:" + serverAddress
//					+ request.getServerInterfaceDefinition().getOpt());
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("POST");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setUseCaches(false);
//			conn.setConnectTimeout(REQUEST_TIME_OUT);
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("Charset", "UTF-8");
//			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
//					+ ";boundary=" + BOUNDARY);
//			// 首先组拼文本类型的参数
//			StringBuilder sb = new StringBuilder();
//			Map<String, String> params = request.getParamsMap();
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				sb.append(PREFIX);
//				sb.append(BOUNDARY);
//				sb.append(LINEND);
//				sb.append("Content-Disposition: form-data; name=\""
//						+ entry.getKey() + "\"" + LINEND);
//				// 这里加上这句话 ，不知道服务端为什么会是乱码
//				// sb.append("Content-Type: text/plain; charset=" + CHARSET +
//				// LINEND);
//				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
//				sb.append(LINEND);
//				sb.append(entry.getValue());
//				sb.append(LINEND);
//
//				LogUtil.log("参数：" + entry.getKey() + "值：" + entry.getValue());
//			}
//			DataOutputStream outStream = new DataOutputStream(
//					conn.getOutputStream());
//			outStream.write(sb.toString().getBytes());
//
//			for (int i = 0; i < formFile.inputStreamValueList.size(); i++) {
//				InputStream inputStreamValue = formFile.inputStreamValueList
//						.get(i).inputStream;
//				/**
//				 * 下面是传图
//				 */
//				StringBuilder sb1 = new StringBuilder();
//				sb1.append(PREFIX);
//				sb1.append(BOUNDARY);
//				sb1.append(LINEND);
//				sb1.append("Content-Disposition: form-data; name=\""
//						+ formFile.inputStreamValueList.get(i).inputStreamKey + "\"; " +
//						"filename=\""
//						+ formFile.inputStreamValueList.get(i).fileName + "\"" + LINEND);
//				sb1.append("Content-Type: image/jpg; charset=" + CHARSET
//						+ LINEND);
//				sb1.append(LINEND);
//				outStream.write(sb1.toString().getBytes());
//				byte[] buffer = new byte[1024];
//				int len = 0;
//				InputStream inputStream = inputStreamValue;
//				if (inputStream != null) {
//					LogUtil.log("返回inputStream == null ? "
//							+ (inputStream == null));
//					while ((len = inputStream.read(buffer)) != -1) {
//						outStream.write(buffer, 0, len);
//					}
//					inputStream.close();
//				}
//				outStream.write(LINEND.getBytes());
//			}
//
//			// 请求结束标志
//			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//			outStream.write(end_data);
//			outStream.flush();
//			int responseCode = conn.getResponseCode();
//			if (responseCode != 200) {
//				LogUtil.log("返回responseCode：" + responseCode);
//				return null;
//			}
//			InputStream in = conn.getInputStream();
//			InputStreamReader isReader = new InputStreamReader(in, CHARSET);
//			BufferedReader bufReader = new BufferedReader(isReader);
//			result = bufReader.readLine();
//			LogUtil.log("返回result：" + result);
//			conn.disconnect();
//			return result;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return null;
	}
}
