package com.qr.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


/**
 * Servlet implementation class QRCodeServlet
 */

@WebServlet("/QRCodeServlet")

public class QRCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QRCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpSerovletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*  get the data from DisplayQRCode.jsp page 
		 *  qrCodeData variable are used to store the actual data(String) of qr image
		 * 	filePath store the QR image in this folder
		 * 	Eight-bit UCS Transformation Format
		 * 	call this createQRCode() method and pass the parameters to thiss method qr code,path of image, height, width.
		 *	call this readQRCode() method for read the QR image and display the actual content(data) of QR image 
		 * 	Send Uname to DisplayQRImage.jsp page to create the new image  by this name
		 * 		 
		*/				
			String Uname=  request.getParameter("Uname");
			String PhoneNumber=  request.getParameter("PhoneNumber");		
			String qrCodeData = "Uname=  "+Uname + "   PhoneNumber=  "+PhoneNumber;	
			
			String imageName = Uname+"_"+PhoneNumber;
			
			
			System.out.println("qrCodeData"+qrCodeData);
			//to store the image to specify path
			String filePath = "D:\\Varsha\\New folder\\QRCode\\WebContent\\image\\"+imageName+".png";	
			//filePath replace the data into original QR image and  generate the new data on same image(just replace the data)
			//String filePath = "D:\\Varsha\\New folder\\QRCode\\WebContent\\image\\QRCode.png";
		
			String charset = "UTF-8"; // or "ISO-8859-1"
			
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
				try {
					createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
					
				} catch (WriterException e) {
					
					e.printStackTrace();
				}
					System.out.println("QR Code image created successfully!");
		
				try {
					System.out.println("Data read from QR Code: "+ readQRCode(filePath, charset, hintMap));
				} catch (NotFoundException e) {
					
					e.printStackTrace();
				}
		
			request.setAttribute("imageName", imageName);
			RequestDispatcher dispatcher = request.getRequestDispatcher("DisplayQRCode.jsp");
			dispatcher.forward( request, response );
			
	}
	public static void createQRCode(String qrCodeData, String filePath,String charset, Map<EncodeHintType, ErrorCorrectionLevel> hintMap, int qrCodeheight, int qrCodewidth)throws WriterException, IOException {		
		
		/*	BitMatrix  is  Encode a barcode using the this argument.
		
		 * 	MultiFormatWriter() is a factory class which finds the appropriate Writer subclass
			for the BarcodeFormat requested and encodes the barcode with the supplied contents.
		
		*	MatrixToImageWriter  Writes a BitMatrix to BufferedImage, file or stream.
		*/
		
	
		BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
															BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
	}

	public static String readQRCode(String filePath, String charset, Map<EncodeHintType, ErrorCorrectionLevel> hintMap)throws FileNotFoundException, IOException, NotFoundException {
		
		/* BinaryBitmap   This class is the core bitmap class used by ZXing to represent 1 bit data. 
		 		Reader objects accept a BinaryBitmap and attempt to decode it.
		   
		 * HybridBinarizer   It is designed for high frequency images of barcodes with black data on white backgrounds.
		 
		 * MultiFormatReader().decode(binaryBitmap);   Decode an image using the binaryBitmap provided.		
		*/
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(
																			ImageIO.read(new FileInputStream(filePath)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);
		return qrCodeResult.getText();

	}	
}
