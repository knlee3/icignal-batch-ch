package icignal.batch.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


/**
 * 문자열 관련 유틸리티
 *
 *
 * @see		#checkByte(String)
 * @see		#convertHangul_English(String)
 * @see		#convertNull(String)
 * @see		#cutStrInByte(String, int)
 * @see		#decodeBase64(String)
 * @see		#decodeUTF8(String)
 * @see		#encodeBase64(String)
 * @see		#getByteBuffer(String)
 * @see		#getCollectionName(Class)
 * @see		#getFixedByte(String, int)
 * @see		#getFixedByte(String, int, int)
 * @see		#getFixedByteArray(String, int)
 * @see		#getFixedNumber(int, int)
 * @see		#getFixedNumber(String, int)
 * @see		#getFixedOneStr(String, int)
 * @see		#getFullFixedByte(String, int)
 * @see		#getFullString(String)
 * @see		#printformat(String)
 * @see		#replace(String, String, String)
 * @see		#strCut(String, int)
 *
 * @author 	dg.ryu
 */
/**
  * @fileName : ICNStringUtility.java
  * @project : iCignal-Common
  * @date : 2017. 1. 13.
  * @author : knlee
  * @변경이력 :
  * @descripton :
  */
public class ICNStringUtility {

	/**
	 * 클래스의 collection 이름을 반환한다
	 *
	 * @param cls	대상이 되는 클래스
	 * @return		collection 이름
	 */
	public synchronized final static String getCollectionName(@SuppressWarnings("rawtypes") Class cls) {
		return cls.getAnnotations()[0].toString().substring(cls.getAnnotations()[0].toString().indexOf("collection") + 11, cls.getAnnotations()[0].toString().length() - 1);
	}

	/**
	 * 대상이 되는 문자열의 각 문자가 1byte인지 여부를 판단하여
	 * 각 문자의 길이에 맞도록(문자가 깨지지 않도록) 지정한 길이 만큼 자른다.
	 * {@link UnsupportedEncodingException} 예외발생시 로그출력
	 *
	 * @param szText	대상이 되는 UTF-8 문자열
	 * @param nLength	문자열 길이
	 * @return			변환된 문자열
	 */
	public synchronized final static String strCut(String szText, int nLength)
	{ // 문자열 자르기
		String r_val = szText;
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		try
		{
			byte[] bytes = r_val.getBytes("UTF-8"); // 바이트로 보관
			// x부터 y길이만큼 잘라낸다. 한글안깨지게.
			int j = 0;
			if (nLengthPrev > 0) {
				while (j < bytes.length)
				{
					if ((bytes[j] & 0x80) != 0)
					{
						oF += 2;
						rF += 3;
						if (oF + 2 > nLengthPrev)
						{
							break;
						}
						j += 3;
					}
					else
					{
						if (oF + 1 > nLengthPrev)
						{
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}
			}
			j = rF;
			while (j < bytes.length)
			{
				if ((bytes[j] & 0x80) != 0)
				{
					if (oL + 2 > nLength)
					{
						break;
					}
					oL += 2;
					rL += 3;
					j += 3;
				}
				else
				{
					if (oL + 1 > nLength)
					{
						break;
					}
					++oL;
					++rL;
					++j;
				}
			}
			r_val = new String(bytes, rF, rL, "UTF-8"); // charset 옵션
		}
		catch (UnsupportedEncodingException e)
		{
			LogUtility.error(e);
		}
		return r_val;
	}

	/**
	 * 문자열의 byte 길이를 반환한다
	 *
	 * @param str	대상이 되는 문자열
	 * @return		바이트 길이
	 */
	public synchronized final static int checkByte(String str) {
		int len = str.length();
		int cnt = 0;

		for(int i=0; i<len; i++){
			if(str.charAt(i) < 256) {
				cnt++;
			} else {
				cnt = cnt + 2;
			}
		}

		return cnt;
	}


	

	/**
	 * 대상 문자열을 UTF-8 방식으로 디코딩하여 반환한다.
	 * {@link UnsupportedEncodingException} 예외발생시 로그출력
	 *
	 * @param str	대상이 되는 문자열
	 * @return		변환된 문자열
	 */
	public synchronized final static String decodeUTF8(String str) {
		String rtnValue = "";
		try {
			rtnValue =URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			
			LogUtility.error(e);
		}
		return rtnValue;
	}

	/**
	 * 대상 문자열을 지정된 캐릭터셋으로 로그 출력하고	
	 * 지정된 캐릭터셋이 아니라면 예외를 던진다
	 *
	 * @param value		대상 문자열
	 * @throws 			UnsupportedEncodingException
	 */
	public synchronized final static void printformat(String value) throws UnsupportedEncodingException{
		String charset[] = {"KSC5601","8859_1", "ascii", "UTF-8", "EUC-KR", "MS949"};


		for(int i=0; i<charset.length ; i++){
			for(int j=0 ; j<charset.length ; j++){
				if(i==j){ continue;}
				else{
					System.out.println(charset[i]+" : "+charset[j]+" :" +new String (value.getBytes(charset[i]),charset[j]));
				}
			}
		}
	}


	/**
	 * null 을 공백으로 바꾼다
	 *
	 * @param str	대상이 되는 문자열
	 * @return		변환된 문자열
	 */
	public synchronized final static String convertNull(String str) {
		if(str == null) {
			str = "";
		}

		return str;
	}


	/**
	  * @name : isNotEmpty
	  * @date : 2017. 1. 11.
	  * @author : knlee
	  * @변경이력 :
	  * @description : 문자열 값이 비어있지 않는지 체크(null , 공백)
	  * @param str 비교할 문자열
	  * @return true: 데이타 존재. false: null 또는 공백
	  */
	public synchronized final static boolean isNotEmpty(String str) {
		if(str == null) return false;
		if("".equals(str.trim())) return false;
		return true;
	}
		


	/**
	  * @name : isEmpty
	  * @date : 2017. 1. 11.
	  * @author : knlee
	  * @table :
	  * @변경이력 :
	  * @description : 문자열 값이 비어 있는지 체크(null , 공백)
	  * @param str
	  * @return true: null 또는 공백. false: 데이터 존재
	  */
	public synchronized final static boolean isEmpty(String str) {
		if(str == null) return true;
		if("".equals(str.trim())) return true;
		return false;
	}
	
	/**
	 * 파라미터 값 모두가 비어 있으면 true
	 * @param strings
	 * @return
	 */
	public synchronized final static boolean isEmptyAll(String ... strings ){
		boolean result = false;
		for (int i = 0; i < strings.length; i++) {
		     if(isEmpty(strings[i])) result = true;
		     else result = false;
		}
		
		return result;
	}
	
	
	
	public synchronized final static boolean isEquals(String str1,  String str2 ){
	    if(str1 == null || str2 == null)  return false;
	    if(str1.equals(str2)) return true;
		return false;
	}
	
	public synchronized final static boolean isUpperCaseEquals(String str1,  String str2 ){
	    if(str1 == null || str2 == null)  return false;
	    if(str1.trim().toUpperCase().equals(str2.trim().toUpperCase())) return true;
		return false;
	}
	
	
	/**
	 * 파라미터 값 모두가 값이 존재하면 true
	 * @param strings
	 * @return
	 */
	public synchronized final static boolean isNotEmptyAll(String ... strings ){
		boolean result = false;
		for (int i = 0; i < strings.length; i++) {
		     if(isNotEmpty(strings[i])) result = true;
		     else return false;
		}
		return result;
	}


	/**
	  * @programId :
	  * @name : trim
	  * @date : 2017. 1. 13.
	  * @author : knlee
	  * @table :
	  * @변경이력 :
	  * @description : 문자열 공백 제거
	  * @param str
	  * @return
	  */
	public synchronized final static String trim(String str){
	  if(ICNStringUtility.isNotEmpty(str)) return str.trim();
	  return str;
	}




	/**
	  * @programId :
	  * @name : extNumber
	  * @date : 2017. 1. 13.
	  * @author : knlee
	  * @table :
	  * @변경이력 :
	  * @description : 문자열에서 숫자만 추출함. ex) 010-1234-5678 -> 01012345678
	  * @param str
	  * @return
	  */
	public synchronized final static String extNumber(String str){

		if(ICNStringUtility.isNotEmpty(str)) return str.replaceAll("\\D+","");
		return str;
	}


	/**
	 * 문자열을 ByteBuffer 에 넣는다
	 *
	 * @param str	대상이 되는 문자열
	 * @return		바이트 버퍼
	 */
	public synchronized final static ByteBuffer getByteBuffer(String str) {
		String tmpStr = str;
		byte[] bytes = tmpStr.getBytes();
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes,0,bytes.length);
		return bb;
	}

	/**
	 * 고정길이 숫자형태의 문자열을 얻는다. 남는자리는 '0'
	 *
	 * @param number	변환할 대상이 되는 문자열
	 * @param len		문자열 길이
	 * @return			변환된 문자열
	 */
	public synchronized final static String getFixedNumber(String number, int len) {
		String str = number;
		str = ICNStringUtility.replace(str, " ", "");
		if(str == null) {
			str = "0";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}

		str = String.valueOf(Integer.parseInt(str));
		StringBuffer sbString = new StringBuffer();

		for(int i=str.length(); i<len; i++) {
			sbString.append("0");
		}

		sbString.append(str);

		return sbString.toString();
	}

	/**
	 *
	 * @param one_str
	 * @param len
	 * @return String
	 */
	public synchronized final static String getFixedOneStr(String one_str, int len) {
		String str = one_str;

		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}

		StringBuffer sbString = new StringBuffer();

		for(int i=str.length(); i<len; i++) {
			sbString.append(one_str);
		}

		sbString.append(str);

		return sbString.toString();
	}

	/**
	 * 고정길이 숫자형태의 문자열을 얻는다. 남는자리는 '0'
	 *
	 * @param number	변환할 대상이 되는 정수
	 * @param len		문자열 길이
	 * @return			변환된 문자열
	 */
	public synchronized final static String getFixedNumber(int number, int len) {
		String str = String.valueOf(number);

		if(str == null) {
			str = "";
		} else if(str.length() > len) {
			str = str.substring(0, len);
		}

		StringBuffer sbString = new StringBuffer();

		for(int i=str.length(); i<len; i++) {
			sbString.append("0");
		}

		sbString.append(str);

		return sbString.toString();
	}

	/**
	 * 고정길이 byte 로 자른 문자열 배열을 얻는다
	 *
	 * @param str			변환할 대상이 되는 문자열
	 * @param byteLength	바이트 길이
	 * @return				변환된 문자열
	 */
	public synchronized final static String[] getFixedByteArray(String str, int byteLength) {

		List<String> aList = new ArrayList<String>();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}
		if(tmpStr.getBytes().length < byteLength) {
			aList.add(getFixedByte(tmpStr, byteLength));
		} else {
			byte[] byteString = tmpStr.getBytes();
			int startPos = 0;
			int endPos = 0+byteLength;

			while(byteString.length>startPos) {
				String tmp = "";
				if(byteString.length >= (startPos+byteLength)) {
					tmp = new String(byteString,startPos,byteLength);
				} else {
					tmp = new String(byteString,startPos,byteString.length-startPos);
				}

				aList.add(getFixedByte(tmp,byteLength));
				startPos = startPos + byteLength;
				endPos = endPos + byteLength;
			}
		}

		String[] strArray = new String[aList.size()];
		aList.toArray(strArray);
		aList.clear();
		return strArray;
	}

	/**
	 * 고정길이 byte 로 자른 문자열을 얻는다
	 *
	 * @param str			변환할 대상이 되는 문자열
	 * @param byteLength	바이트 길이
	 * @return				변환된 문자열
	 */
	public synchronized final static String getFullFixedByte(String str, int byteLength) {

		str = getFullString(str);
		str = getFixedByte(str, byteLength-2);
		str = str + "  ";
		return str;
	}

	/**
	 * 고정길이 byte 로 자른 문자열을 얻는다
	 *
	 * @param str			변환할 대상이 되는 문자열
	 * @param byteLength	바이트 길이
	 * @return				변환된 문자열
	 */
	public synchronized final static String getFixedByte(String str, int byteLength) {
		StringBuffer sbString = new StringBuffer();
		String tmpStr = str;
		if(tmpStr == null) {
			tmpStr = "";
		}

		sbString.append(tmpStr);

		for(int i=tmpStr.getBytes().length; i<byteLength; i++) {
			sbString.append(" ");
		}
		tmpStr = sbString.toString();
		byte[] byteString = tmpStr.getBytes();

		return new String(byteString,0,byteLength);
	}

	/**
	 * 시작위치로 부터 특정 길이의 고정길이 문자열을 얻는다
	 *
	 * @param str		변환할 대상이 되는 문자열
	 * @param startPos	시작 위치
	 * @param endPos	끝 위치
	 * @return			변환된 문자열
	 */
	public synchronized final static String getFixedByte(String str, int startPos, int endPos) {
		String tmpStr = str;
		tmpStr = getFixedByte(tmpStr, endPos);
		return new String(tmpStr.getBytes(), startPos, endPos-startPos);
	}

	/**
	 * 반자를 전자로 변환한다
	 *
	 * @param 	strHalf	변환할 대상이 되는 문자열
	 * @return			변환된 문자열
	 */
	public synchronized final static String getFullString(String strHalf) {

		if(strHalf == null) {
			return strHalf;
		}

		char[] charArray = strHalf.toCharArray();

		for(int i=0; i<charArray.length; i++) {
			if(charArray[i] >= 0x21 && charArray[i] <= 0x7E) {
				// 반자 영문 영숫자/문자부호를 골라, 0xFEE0 만큼 더한다.
				charArray[i] += 0xFEE0;
			} else if(charArray[i] == 0x20) {
				// 반자 공백을 전자 공백으로 바꾼다.
				charArray[i] = 0x3000;
			}
		}

		String strFull = new String(charArray);

		return strFull;
	}

	/**
	 * 전자를 반자로 변환한다
	 *
	 * @param 	strFull	변환할 대상이 되는 문자열
	 * @return			변환된 문자열
	 */
	public synchronized final static String getHalfString(String strFull) {

		if(strFull == null) {
			return strFull;
		}

		char[] charArray = strFull.toCharArray();

		for(int i=0; i<charArray.length; i++) {
			if(charArray[i] >= 0xFF01 && charArray[i] <= 0xFF5E) {
				// 전자 영문 영숫자/문자부호를 골라, 0xFEE0 만큼 뺀다.
				charArray[i] -= 0xFEE0;
			} else if(charArray[i] == 0x3000) {
				// 전자 공백을 반자 공백으로 바꾼다.
				charArray[i] = 0x20;
			}
		}

		String strHalf = new String(charArray);
		return strHalf;
	}

	/**
	 * 문자열에서 특정 문자열을 찾아서 대체 문자열로 대체하여 반환한다.
	 *
	 * @param str	변환할 대상이 되는 문자열
	 * @param src	찾을 문자열
	 * @param dst	대체할 문자열
	 * @return		변경된 문자열
	 */
	public static String replace(String str, String src, String dst)
	{
		if(str == null || src == null || dst == null || str.length() == 0 || src.length() == 0) {
			return str;
		}

		int srcLength = src.length(), pos = 0, dstLength = dst.length();
		while(true) {

			if((pos = str.indexOf(src,pos)) != -1) {
				if((pos+srcLength) < str.length()) {
					str = str.substring(0,pos) + dst + str.substring(pos+srcLength);
				} else {
					str = str.substring(0,pos) + dst;
				}

				pos = pos + dstLength;

				if(pos > str.length()) {
					pos = str.length();
				}
			} else {
				break;
			}
		}
		return str;
	}

	/**
	 * 대상 문자열을 구분자로 나눠서 문자열 배열로 반환
	 *
	 * @param str			변환할 대상이 되는 문자열
	 * @param separator		문자열 구분자
	 * @return				문자열 배열
	 */
	public synchronized final static String[] getStringArray(String str, String separator) {
		str = ICNStringUtility.replace(str, separator, " " + separator);

		StringTokenizer strToken = new StringTokenizer(str, separator);
		String[] strArray = null;

		List<String> list = new ArrayList<String>();

		while (strToken.hasMoreTokens()) {
			list.add(strToken.nextToken().toString().trim());
		}

		strArray = new String[list.size()];
		list.toArray(strArray);
		list.clear();

		return strArray;
	}

	/**
	 *
	 * @param str
	 * @param separator
	 * @return String[]
	 */
	public synchronized final static String[] getStringArrayTelNo(String str, String separator) {
		str = ICNStringUtility.replace(str, separator, " " + separator);

		StringTokenizer strToken = new StringTokenizer(str, separator);
		String[] strArray = null;
		String[] strArrayTelNo = new String[3];
		strArrayTelNo[0] = "";
		strArrayTelNo[1] = "";
		strArrayTelNo[2] = "";


		List<String> list = new ArrayList<String>();

		while (strToken.hasMoreTokens()) {
			list.add(strToken.nextToken().toString().trim());
		}

		strArray = new String[list.size()];
		list.toArray(strArray);
		list.clear();

		for(int i=0; i<strArray.length; i++) {
			strArrayTelNo[2-i] = strArray[strArray.length-i-1];
		}

		return strArrayTelNo;
	}

	/**
	 * 문자열이 최대자릿수 보다 크면,
	 * 문자열을 최대자릿수 만큼 자른 후 '...'을 끝에 붙여서 반환
	 *
	 * @param str		변환할 대상이 되는 문자열
	 * @param endIndex	변환할 문자열 최대자릿수
	 * @return			변환된 문자열
	 */
	public synchronized final static String cutStrInByte(String str, int endIndex)
	{
		StringBuffer sbStr = new StringBuffer(endIndex);
		int iTotal=0;
		char[] chars = str.toCharArray();

		for(int i=0; i<chars.length; i++)
		{
			iTotal+=String.valueOf(chars[i]).getBytes().length;
			if(iTotal>endIndex)
			{
				sbStr.append("...");  // ... 붙일것인가 옵션
				break;
			}
			sbStr.append(chars[i]);
		}
		return sbStr.toString();
	}

	/**
	 * 한글을 영문으로 변환한다
	 *
	 * @param str	변환할 한글 문자열
	 * @return		변환된 영문 문자열
	 */
	public synchronized final static String convertHangul_English(String str) {

		String tmpStr = str;
		if(tmpStr.indexOf("사용자") >= 0) {
			tmpStr = tmpStr.replaceAll("사용자","User");
		} else if(tmpStr.indexOf("프로그램") >= 0) {
			tmpStr = tmpStr.replaceAll("프로그램","Pgm");
		} else if(tmpStr.indexOf("재무재표") >= 0) {
			tmpStr = tmpStr.replaceAll("재무재표","BSPL");
		} else if(tmpStr.indexOf("부가가치세") >= 0) {
			tmpStr = tmpStr.replaceAll("부가가치세","VAT");
		} else if(tmpStr.indexOf("생성") >= 0) {
			tmpStr = tmpStr.replaceAll("생성","Crt");
		} else if(tmpStr.indexOf("코드") >= 0) {
			tmpStr = tmpStr.replaceAll("코드","Cd");
		} else if(tmpStr.indexOf("문서") >= 0) {
			tmpStr = tmpStr.replaceAll("문서","Doc");
		} else if(tmpStr.indexOf("보고서") >= 0) {
			tmpStr = tmpStr.replaceAll("보고서","Rpt");
		} else if(tmpStr.indexOf("공지사항") >= 0) {
			tmpStr = tmpStr.replaceAll("공지사항","Notice");
		} else if(tmpStr.indexOf("공지") >= 0) {
			tmpStr = tmpStr.replaceAll("공지","Notice");
		} else if(tmpStr.indexOf("카드") >= 0) {
			tmpStr = tmpStr.replaceAll("카드","Card");
		}

		tmpStr = tmpStr.replaceAll( "가", "Ga");
		tmpStr = tmpStr.replaceAll( "각", "Gak");
		tmpStr = tmpStr.replaceAll( "간", "Gan");
		tmpStr = tmpStr.replaceAll( "갈", "Gal");
		tmpStr = tmpStr.replaceAll( "감", "Gam");
		tmpStr = tmpStr.replaceAll( "갑", "Gap");
		tmpStr = tmpStr.replaceAll( "갓", "Gat");
		tmpStr = tmpStr.replaceAll( "강", "Gang");
		tmpStr = tmpStr.replaceAll( "개", "Gae");
		tmpStr = tmpStr.replaceAll( "객", "Gaek");
		tmpStr = tmpStr.replaceAll( "거", "Geo");
		tmpStr = tmpStr.replaceAll( "건", "Geon");
		tmpStr = tmpStr.replaceAll( "걸", "Geol");
		tmpStr = tmpStr.replaceAll( "검", "Geom");
		tmpStr = tmpStr.replaceAll( "겁", "Geop");
		tmpStr = tmpStr.replaceAll( "게", "Ge");
		tmpStr = tmpStr.replaceAll( "겨", "Gyeo");
		tmpStr = tmpStr.replaceAll( "격", "Gyeok");
		tmpStr = tmpStr.replaceAll( "견", "Gyeon");
		tmpStr = tmpStr.replaceAll( "결", "Gyeol");
		tmpStr = tmpStr.replaceAll( "겸", "Gyeom");
		tmpStr = tmpStr.replaceAll( "겹", "Gyeop");
		tmpStr = tmpStr.replaceAll( "경", "Gyeong");
		tmpStr = tmpStr.replaceAll( "계", "Gye");
		tmpStr = tmpStr.replaceAll( "고", "Go");
		tmpStr = tmpStr.replaceAll( "곡", "Gok");
		tmpStr = tmpStr.replaceAll( "곤", "Gon");
		tmpStr = tmpStr.replaceAll( "골", "Gol");
		tmpStr = tmpStr.replaceAll( "곳", "Got");
		tmpStr = tmpStr.replaceAll( "공", "Gong");
		tmpStr = tmpStr.replaceAll( "곶", "Got");
		tmpStr = tmpStr.replaceAll( "과", "Gwa");
		tmpStr = tmpStr.replaceAll( "곽", "Gwak");
		tmpStr = tmpStr.replaceAll( "관", "Gwan");
		tmpStr = tmpStr.replaceAll( "괄", "Gwal");
		tmpStr = tmpStr.replaceAll( "광", "Gwang");
		tmpStr = tmpStr.replaceAll( "괘", "Gwae");
		tmpStr = tmpStr.replaceAll( "괴", "Goe");
		tmpStr = tmpStr.replaceAll( "굉", "Goeng");
		tmpStr = tmpStr.replaceAll( "교", "Gyo");
		tmpStr = tmpStr.replaceAll( "구", "Gu");
		tmpStr = tmpStr.replaceAll( "국", "Guk");
		tmpStr = tmpStr.replaceAll( "군", "Gun");
		tmpStr = tmpStr.replaceAll( "굴", "Gul");
		tmpStr = tmpStr.replaceAll( "굿", "Gut");
		tmpStr = tmpStr.replaceAll( "궁", "Gung");
		tmpStr = tmpStr.replaceAll( "권", "Gwon");
		tmpStr = tmpStr.replaceAll( "궐", "Gwol");
		tmpStr = tmpStr.replaceAll( "귀", "Gwi");
		tmpStr = tmpStr.replaceAll( "규", "Gyu");
		tmpStr = tmpStr.replaceAll( "균", "Gyun");
		tmpStr = tmpStr.replaceAll( "귤", "Gyul");
		tmpStr = tmpStr.replaceAll( "그", "Geu");
		tmpStr = tmpStr.replaceAll( "극", "Geuk");
		tmpStr = tmpStr.replaceAll( "근", "Geun");
		tmpStr = tmpStr.replaceAll( "글", "Geul");
		tmpStr = tmpStr.replaceAll( "금", "Geum");
		tmpStr = tmpStr.replaceAll( "급", "Geup");
		tmpStr = tmpStr.replaceAll( "긍", "Geung");
		tmpStr = tmpStr.replaceAll( "기", "Gi");
		tmpStr = tmpStr.replaceAll( "긴", "Gin");
		tmpStr = tmpStr.replaceAll( "길", "Gil");
		tmpStr = tmpStr.replaceAll( "김", "Gim");
		tmpStr = tmpStr.replaceAll( "까", "Kka");
		tmpStr = tmpStr.replaceAll( "깨", "Kkae");
		tmpStr = tmpStr.replaceAll( "꼬", "Kko");
		tmpStr = tmpStr.replaceAll( "꼭", "Kkok");
		tmpStr = tmpStr.replaceAll( "꽃", "Kkot");
		tmpStr = tmpStr.replaceAll( "꾀", "Kkoe");
		tmpStr = tmpStr.replaceAll( "꾸", "Kku");
		tmpStr = tmpStr.replaceAll( "꿈", "Kkum");
		tmpStr = tmpStr.replaceAll( "끝", "Kkeut");
		tmpStr = tmpStr.replaceAll( "끼", "Kki");
		tmpStr = tmpStr.replaceAll( "나", "Na");
		tmpStr = tmpStr.replaceAll( "낙", "Nak");
		tmpStr = tmpStr.replaceAll( "난", "Nan");
		tmpStr = tmpStr.replaceAll( "날", "Nal");
		tmpStr = tmpStr.replaceAll( "남", "Nam");
		tmpStr = tmpStr.replaceAll( "납", "Nap");
		tmpStr = tmpStr.replaceAll( "낭", "Nang");
		tmpStr = tmpStr.replaceAll( "내", "Nae");
		tmpStr = tmpStr.replaceAll( "냉", "Naeng");
		tmpStr = tmpStr.replaceAll( "너", "Neo");
		tmpStr = tmpStr.replaceAll( "널", "Neol");
		tmpStr = tmpStr.replaceAll( "네", "Ne");
		tmpStr = tmpStr.replaceAll( "녀", "Nyeo");
		tmpStr = tmpStr.replaceAll( "녁", "Nyeok");
		tmpStr = tmpStr.replaceAll( "년", "Nyeon");
		tmpStr = tmpStr.replaceAll( "념", "Nyeom");
		tmpStr = tmpStr.replaceAll( "녕", "Nyeong");
		tmpStr = tmpStr.replaceAll( "노", "No");
		tmpStr = tmpStr.replaceAll( "녹", "Nok");
		tmpStr = tmpStr.replaceAll( "논", "Non");
		tmpStr = tmpStr.replaceAll( "놀", "Nol");
		tmpStr = tmpStr.replaceAll( "농", "Nong");
		tmpStr = tmpStr.replaceAll( "뇌", "Noe");
		tmpStr = tmpStr.replaceAll( "누", "Nu");
		tmpStr = tmpStr.replaceAll( "눈", "Nun");
		tmpStr = tmpStr.replaceAll( "눌", "Nul");
		tmpStr = tmpStr.replaceAll( "느", "Neu");
		tmpStr = tmpStr.replaceAll( "늑", "Neuk");
		tmpStr = tmpStr.replaceAll( "늠", "Neum");
		tmpStr = tmpStr.replaceAll( "능", "Neung");
		tmpStr = tmpStr.replaceAll( "늬", "Nui");
		tmpStr = tmpStr.replaceAll( "니", "Ni");
		tmpStr = tmpStr.replaceAll( "닉", "Nik");
		tmpStr = tmpStr.replaceAll( "닌", "Nin");
		tmpStr = tmpStr.replaceAll( "닐", "Nil");
		tmpStr = tmpStr.replaceAll( "님", "Nim");
		tmpStr = tmpStr.replaceAll( "다", "Da");
		tmpStr = tmpStr.replaceAll( "단", "Dan");
		tmpStr = tmpStr.replaceAll( "달", "Dal");
		tmpStr = tmpStr.replaceAll( "담", "Dam");
		tmpStr = tmpStr.replaceAll( "답", "Dap");
		tmpStr = tmpStr.replaceAll( "당", "Dang");
		tmpStr = tmpStr.replaceAll( "대", "Dae");
		tmpStr = tmpStr.replaceAll( "댁", "Daek");
		tmpStr = tmpStr.replaceAll( "더", "Deo");
		tmpStr = tmpStr.replaceAll( "덕", "Deok");
		tmpStr = tmpStr.replaceAll( "도", "Do");
		tmpStr = tmpStr.replaceAll( "독", "Dok");
		tmpStr = tmpStr.replaceAll( "돈", "Don");
		tmpStr = tmpStr.replaceAll( "돌", "Dol");
		tmpStr = tmpStr.replaceAll( "동", "Dong");
		tmpStr = tmpStr.replaceAll( "돼", "Dwae");
		tmpStr = tmpStr.replaceAll( "되", "Doe");
		tmpStr = tmpStr.replaceAll( "된", "Doen");
		tmpStr = tmpStr.replaceAll( "두", "Du");
		tmpStr = tmpStr.replaceAll( "둑", "Duk");
		tmpStr = tmpStr.replaceAll( "둔", "Dun");
		tmpStr = tmpStr.replaceAll( "뒤", "Dwi");
		tmpStr = tmpStr.replaceAll( "드", "Deu");
		tmpStr = tmpStr.replaceAll( "득", "Deuk");
		tmpStr = tmpStr.replaceAll( "들", "Deul");
		tmpStr = tmpStr.replaceAll( "등", "Deung");
		tmpStr = tmpStr.replaceAll( "디", "Di");
		tmpStr = tmpStr.replaceAll( "따", "Tta");
		tmpStr = tmpStr.replaceAll( "땅", "Ttang");
		tmpStr = tmpStr.replaceAll( "때", "Ttae");
		tmpStr = tmpStr.replaceAll( "또", "Tto");
		tmpStr = tmpStr.replaceAll( "뚜", "Ttu");
		tmpStr = tmpStr.replaceAll( "뚝", "Ttuk");
		tmpStr = tmpStr.replaceAll( "뜨", "Tteu");
		tmpStr = tmpStr.replaceAll( "띠", "Tti");
		tmpStr = tmpStr.replaceAll( "라", "Ra");
		tmpStr = tmpStr.replaceAll( "락", "Rak");
		tmpStr = tmpStr.replaceAll( "란", "Ran");
		tmpStr = tmpStr.replaceAll( "람", "Ram");
		tmpStr = tmpStr.replaceAll( "랑", "Rang");
		tmpStr = tmpStr.replaceAll( "래", "Rae");
		tmpStr = tmpStr.replaceAll( "랭", "Raeng");
		tmpStr = tmpStr.replaceAll( "량", "Ryang");
		tmpStr = tmpStr.replaceAll( "렁", "Reong");
		tmpStr = tmpStr.replaceAll( "레", "Re");
		tmpStr = tmpStr.replaceAll( "려", "Ryeo");
		tmpStr = tmpStr.replaceAll( "력", "Ryeok");
		tmpStr = tmpStr.replaceAll( "련", "Ryeon");
		tmpStr = tmpStr.replaceAll( "렬", "Ryeol");
		tmpStr = tmpStr.replaceAll( "렴", "Ryeom");
		tmpStr = tmpStr.replaceAll( "렵", "Ryeop");
		tmpStr = tmpStr.replaceAll( "령", "Ryeong");
		tmpStr = tmpStr.replaceAll( "례", "Rye");
		tmpStr = tmpStr.replaceAll( "로", "Ro");
		tmpStr = tmpStr.replaceAll( "록", "Rok");
		tmpStr = tmpStr.replaceAll( "론", "Ron");
		tmpStr = tmpStr.replaceAll( "롱", "Rong");
		tmpStr = tmpStr.replaceAll( "뢰", "Roe");
		tmpStr = tmpStr.replaceAll( "료", "Ryo");
		tmpStr = tmpStr.replaceAll( "룡", "Ryong");
		tmpStr = tmpStr.replaceAll( "루", "Ru");
		tmpStr = tmpStr.replaceAll( "류", "Ryu");
		tmpStr = tmpStr.replaceAll( "륙", "Ryuk");
		tmpStr = tmpStr.replaceAll( "륜", "Ryun");
		tmpStr = tmpStr.replaceAll( "률", "Ryul");
		tmpStr = tmpStr.replaceAll( "륭", "Ryung");
		tmpStr = tmpStr.replaceAll( "르", "Reu");
		tmpStr = tmpStr.replaceAll( "륵", "Reuk");
		tmpStr = tmpStr.replaceAll( "른", "Reun");
		tmpStr = tmpStr.replaceAll( "름", "Reum");
		tmpStr = tmpStr.replaceAll( "릉", "Reung");
		tmpStr = tmpStr.replaceAll( "리", "Ri");
		tmpStr = tmpStr.replaceAll( "린", "Rin");
		tmpStr = tmpStr.replaceAll( "림", "Rim");
		tmpStr = tmpStr.replaceAll( "립", "Rip");
		tmpStr = tmpStr.replaceAll( "마", "Ma");
		tmpStr = tmpStr.replaceAll( "막", "Mak");
		tmpStr = tmpStr.replaceAll( "만", "Man");
		tmpStr = tmpStr.replaceAll( "말", "Mal");
		tmpStr = tmpStr.replaceAll( "망", "Mang");
		tmpStr = tmpStr.replaceAll( "매", "Mae");
		tmpStr = tmpStr.replaceAll( "맥", "Maek");
		tmpStr = tmpStr.replaceAll( "맨", "Maen");
		tmpStr = tmpStr.replaceAll( "맹", "Maeng");
		tmpStr = tmpStr.replaceAll( "머", "Meo");
		tmpStr = tmpStr.replaceAll( "먹", "Meok");
		tmpStr = tmpStr.replaceAll( "메", "Me");
		tmpStr = tmpStr.replaceAll( "며", "Myeo");
		tmpStr = tmpStr.replaceAll( "멱", "Myeok");
		tmpStr = tmpStr.replaceAll( "면", "Myeon");
		tmpStr = tmpStr.replaceAll( "멸", "Myeol");
		tmpStr = tmpStr.replaceAll( "명", "Myeong");
		tmpStr = tmpStr.replaceAll( "모", "Mo");
		tmpStr = tmpStr.replaceAll( "목", "Mok");
		tmpStr = tmpStr.replaceAll( "몰", "Mol");
		tmpStr = tmpStr.replaceAll( "못", "Mot");
		tmpStr = tmpStr.replaceAll( "몽", "Mong");
		tmpStr = tmpStr.replaceAll( "뫼", "Moe");
		tmpStr = tmpStr.replaceAll( "묘", "Myo");
		tmpStr = tmpStr.replaceAll( "무", "Mu");
		tmpStr = tmpStr.replaceAll( "묵", "Muk");
		tmpStr = tmpStr.replaceAll( "문", "Mun");
		tmpStr = tmpStr.replaceAll( "물", "Mul");
		tmpStr = tmpStr.replaceAll( "므", "Meu");
		tmpStr = tmpStr.replaceAll( "미", "Mi");
		tmpStr = tmpStr.replaceAll( "민", "Min");
		tmpStr = tmpStr.replaceAll( "밀", "Mil");
		tmpStr = tmpStr.replaceAll( "바", "Ba");
		tmpStr = tmpStr.replaceAll( "박", "Bak");
		tmpStr = tmpStr.replaceAll( "반", "Ban");
		tmpStr = tmpStr.replaceAll( "발", "Bal");
		tmpStr = tmpStr.replaceAll( "밥", "Bap");
		tmpStr = tmpStr.replaceAll( "방", "Bang");
		tmpStr = tmpStr.replaceAll( "배", "Bae");
		tmpStr = tmpStr.replaceAll( "백", "Baek");
		tmpStr = tmpStr.replaceAll( "뱀", "Baem");
		tmpStr = tmpStr.replaceAll( "버", "Beo");
		tmpStr = tmpStr.replaceAll( "번", "Beon");
		tmpStr = tmpStr.replaceAll( "벌", "Beol");
		tmpStr = tmpStr.replaceAll( "범", "Beom");
		tmpStr = tmpStr.replaceAll( "법", "Beop");
		tmpStr = tmpStr.replaceAll( "벼", "Byeo");
		tmpStr = tmpStr.replaceAll( "벽", "Byeok");
		tmpStr = tmpStr.replaceAll( "변", "Byeon");
		tmpStr = tmpStr.replaceAll( "별", "Byeol");
		tmpStr = tmpStr.replaceAll( "병", "Byeong");
		tmpStr = tmpStr.replaceAll( "보", "Bo");
		tmpStr = tmpStr.replaceAll( "복", "Bok");
		tmpStr = tmpStr.replaceAll( "본", "Bon");
		tmpStr = tmpStr.replaceAll( "봉", "Bong");
		tmpStr = tmpStr.replaceAll( "부", "Bu");
		tmpStr = tmpStr.replaceAll( "북", "Buk");
		tmpStr = tmpStr.replaceAll( "분", "Bun");
		tmpStr = tmpStr.replaceAll( "불", "Bul");
		tmpStr = tmpStr.replaceAll( "붕", "Bung");
		tmpStr = tmpStr.replaceAll( "비", "Bi");
		tmpStr = tmpStr.replaceAll( "빈", "Bin");
		tmpStr = tmpStr.replaceAll( "빌", "Bil");
		tmpStr = tmpStr.replaceAll( "빔", "Bim");
		tmpStr = tmpStr.replaceAll( "빙", "Bing");
		tmpStr = tmpStr.replaceAll( "빠", "Ppa");
		tmpStr = tmpStr.replaceAll( "빼", "Ppae");
		tmpStr = tmpStr.replaceAll( "뻐", "Ppeo");
		tmpStr = tmpStr.replaceAll( "뽀", "Ppo");
		tmpStr = tmpStr.replaceAll( "뿌", "Ppu");
		tmpStr = tmpStr.replaceAll( "쁘", "Ppeu");
		tmpStr = tmpStr.replaceAll( "삐", "Ppi");
		tmpStr = tmpStr.replaceAll( "사", "Sa");
		tmpStr = tmpStr.replaceAll( "삭", "Sak");
		tmpStr = tmpStr.replaceAll( "산", "San");
		tmpStr = tmpStr.replaceAll( "살", "Sal");
		tmpStr = tmpStr.replaceAll( "삼", "Sam");
		tmpStr = tmpStr.replaceAll( "삽", "Sap");
		tmpStr = tmpStr.replaceAll( "상", "Sang");
		tmpStr = tmpStr.replaceAll( "샅", "Sat");
		tmpStr = tmpStr.replaceAll( "새", "Sae");
		tmpStr = tmpStr.replaceAll( "색", "Saek");
		tmpStr = tmpStr.replaceAll( "생", "Saeng");
		tmpStr = tmpStr.replaceAll( "서", "Seo");
		tmpStr = tmpStr.replaceAll( "석", "Seok");
		tmpStr = tmpStr.replaceAll( "선", "Seon");
		tmpStr = tmpStr.replaceAll( "설", "Seol");
		tmpStr = tmpStr.replaceAll( "섬", "Seom");
		tmpStr = tmpStr.replaceAll( "섭", "Seop");
		tmpStr = tmpStr.replaceAll( "성", "Seong");
		tmpStr = tmpStr.replaceAll( "세", "Se");
		tmpStr = tmpStr.replaceAll( "셔", "Syeo");
		tmpStr = tmpStr.replaceAll( "소", "So");
		tmpStr = tmpStr.replaceAll( "속", "Sok");
		tmpStr = tmpStr.replaceAll( "손", "Son");
		tmpStr = tmpStr.replaceAll( "솔", "Sol");
		tmpStr = tmpStr.replaceAll( "솟", "Sot");
		tmpStr = tmpStr.replaceAll( "송", "Song");
		tmpStr = tmpStr.replaceAll( "쇄", "Swae");
		tmpStr = tmpStr.replaceAll( "쇠", "Soe");
		tmpStr = tmpStr.replaceAll( "수", "Su");
		tmpStr = tmpStr.replaceAll( "숙", "Suk");
		tmpStr = tmpStr.replaceAll( "순", "Sun");
		tmpStr = tmpStr.replaceAll( "술", "Sul");
		tmpStr = tmpStr.replaceAll( "숨", "Sum");
		tmpStr = tmpStr.replaceAll( "숭", "Sung");
		tmpStr = tmpStr.replaceAll( "쉬", "Swi");
		tmpStr = tmpStr.replaceAll( "스", "Seu");
		tmpStr = tmpStr.replaceAll( "슬", "Seul");
		tmpStr = tmpStr.replaceAll( "슴", "Seum");
		tmpStr = tmpStr.replaceAll( "습", "Seup");
		tmpStr = tmpStr.replaceAll( "승", "Seung");
		tmpStr = tmpStr.replaceAll( "시", "Si");
		tmpStr = tmpStr.replaceAll( "식", "Sik");
		tmpStr = tmpStr.replaceAll( "신", "Sin");
		tmpStr = tmpStr.replaceAll( "실", "Sil");
		tmpStr = tmpStr.replaceAll( "심", "Sim");
		tmpStr = tmpStr.replaceAll( "십", "Sip");
		tmpStr = tmpStr.replaceAll( "싱", "Sing");
		tmpStr = tmpStr.replaceAll( "싸", "Ssa");
		tmpStr = tmpStr.replaceAll( "쌍", "Ssang");
		tmpStr = tmpStr.replaceAll( "쌔", "Ssae");
		tmpStr = tmpStr.replaceAll( "쏘", "Sso");
		tmpStr = tmpStr.replaceAll( "쑥", "Ssuk");
		tmpStr = tmpStr.replaceAll( "씨", "Ssi");
		tmpStr = tmpStr.replaceAll( "아", "A");
		tmpStr = tmpStr.replaceAll( "악", "Ak");
		tmpStr = tmpStr.replaceAll( "안", "An");
		tmpStr = tmpStr.replaceAll( "알", "Al");
		tmpStr = tmpStr.replaceAll( "암", "Am");
		tmpStr = tmpStr.replaceAll( "압", "Ap");
		tmpStr = tmpStr.replaceAll( "앙", "Ang");
		tmpStr = tmpStr.replaceAll( "앞", "Ap");
		tmpStr = tmpStr.replaceAll( "애", "Ae");
		tmpStr = tmpStr.replaceAll( "액", "Aek");
		tmpStr = tmpStr.replaceAll( "앵", "Aeng");
		tmpStr = tmpStr.replaceAll( "야", "Ya");
		tmpStr = tmpStr.replaceAll( "약", "Yak");
		tmpStr = tmpStr.replaceAll( "얀", "Yan");
		tmpStr = tmpStr.replaceAll( "양", "Yang");
		tmpStr = tmpStr.replaceAll( "어", "Eo");
		tmpStr = tmpStr.replaceAll( "억", "Eok");
		tmpStr = tmpStr.replaceAll( "언", "Eon");
		tmpStr = tmpStr.replaceAll( "얼", "Eol");
		tmpStr = tmpStr.replaceAll( "엄", "Eom");
		tmpStr = tmpStr.replaceAll( "업", "Eop");
		tmpStr = tmpStr.replaceAll( "에", "E");
		tmpStr = tmpStr.replaceAll( "여", "Yeo");
		tmpStr = tmpStr.replaceAll( "역", "Yeok");
		tmpStr = tmpStr.replaceAll( "연", "Yeon");
		tmpStr = tmpStr.replaceAll( "열", "Yeol");
		tmpStr = tmpStr.replaceAll( "염", "Yeom");
		tmpStr = tmpStr.replaceAll( "엽", "Yeop");
		tmpStr = tmpStr.replaceAll( "영", "Yeong");
		tmpStr = tmpStr.replaceAll( "예", "Ye");
		tmpStr = tmpStr.replaceAll( "오", "O");
		tmpStr = tmpStr.replaceAll( "옥", "Ok");
		tmpStr = tmpStr.replaceAll( "온", "On");
		tmpStr = tmpStr.replaceAll( "올", "Ol");
		tmpStr = tmpStr.replaceAll( "옴", "Om");
		tmpStr = tmpStr.replaceAll( "옹", "Ong");
		tmpStr = tmpStr.replaceAll( "와", "Wa");
		tmpStr = tmpStr.replaceAll( "완", "Wan");
		tmpStr = tmpStr.replaceAll( "왈", "Wal");
		tmpStr = tmpStr.replaceAll( "왕", "Wang");
		tmpStr = tmpStr.replaceAll( "왜", "Wae");
		tmpStr = tmpStr.replaceAll( "외", "Oe");
		tmpStr = tmpStr.replaceAll( "왼", "Oen");
		tmpStr = tmpStr.replaceAll( "요", "Yo");
		tmpStr = tmpStr.replaceAll( "욕", "Yok");
		tmpStr = tmpStr.replaceAll( "용", "Yong");
		tmpStr = tmpStr.replaceAll( "우", "U");
		tmpStr = tmpStr.replaceAll( "욱", "Uk");
		tmpStr = tmpStr.replaceAll( "운", "Un");
		tmpStr = tmpStr.replaceAll( "울", "Ul");
		tmpStr = tmpStr.replaceAll( "움", "Um");
		tmpStr = tmpStr.replaceAll( "웅", "Ung");
		tmpStr = tmpStr.replaceAll( "워", "Wo");
		tmpStr = tmpStr.replaceAll( "원", "Won");
		tmpStr = tmpStr.replaceAll( "월", "Wol");
		tmpStr = tmpStr.replaceAll( "위", "Wi");
		tmpStr = tmpStr.replaceAll( "유", "Yu");
		tmpStr = tmpStr.replaceAll( "육", "Yuk");
		tmpStr = tmpStr.replaceAll( "윤", "Yun");
		tmpStr = tmpStr.replaceAll( "율", "Yul");
		tmpStr = tmpStr.replaceAll( "융", "Yung");
		tmpStr = tmpStr.replaceAll( "윷", "Yut");
		tmpStr = tmpStr.replaceAll( "으", "Eu");
		tmpStr = tmpStr.replaceAll( "은", "Eun");
		tmpStr = tmpStr.replaceAll( "을", "Eul");
		tmpStr = tmpStr.replaceAll( "음", "Eum");
		tmpStr = tmpStr.replaceAll( "읍", "Eup");
		tmpStr = tmpStr.replaceAll( "응", "Eung");
		tmpStr = tmpStr.replaceAll( "의", "Ui");
		tmpStr = tmpStr.replaceAll( "이", "I");
		tmpStr = tmpStr.replaceAll( "익", "Ik");
		tmpStr = tmpStr.replaceAll( "인", "In");
		tmpStr = tmpStr.replaceAll( "일", "Il");
		tmpStr = tmpStr.replaceAll( "임", "Im");
		tmpStr = tmpStr.replaceAll( "입", "Ip");
		tmpStr = tmpStr.replaceAll( "잉", "Ing");
		tmpStr = tmpStr.replaceAll( "자", "Ja");
		tmpStr = tmpStr.replaceAll( "작", "Jak");
		tmpStr = tmpStr.replaceAll( "잔", "Jan");
		tmpStr = tmpStr.replaceAll( "잠", "Jam");
		tmpStr = tmpStr.replaceAll( "잡", "Jap");
		tmpStr = tmpStr.replaceAll( "장", "Jang");
		tmpStr = tmpStr.replaceAll( "재", "Jae");
		tmpStr = tmpStr.replaceAll( "쟁", "Jaeng");
		tmpStr = tmpStr.replaceAll( "저", "Jeo");
		tmpStr = tmpStr.replaceAll( "적", "Jeok");
		tmpStr = tmpStr.replaceAll( "전", "Jeon");
		tmpStr = tmpStr.replaceAll( "절", "Jeol");
		tmpStr = tmpStr.replaceAll( "점", "Jeom");
		tmpStr = tmpStr.replaceAll( "접", "Jeop");
		tmpStr = tmpStr.replaceAll( "정", "Jeong");
		tmpStr = tmpStr.replaceAll( "제", "Je");
		tmpStr = tmpStr.replaceAll( "조", "Jo");
		tmpStr = tmpStr.replaceAll( "족", "Jok");
		tmpStr = tmpStr.replaceAll( "존", "Jon");
		tmpStr = tmpStr.replaceAll( "졸", "Jol");
		tmpStr = tmpStr.replaceAll( "종", "Jong");
		tmpStr = tmpStr.replaceAll( "좌", "Jwa");
		tmpStr = tmpStr.replaceAll( "죄", "Joe");
		tmpStr = tmpStr.replaceAll( "주", "Ju");
		tmpStr = tmpStr.replaceAll( "죽", "Juk");
		tmpStr = tmpStr.replaceAll( "준", "Jun");
		tmpStr = tmpStr.replaceAll( "줄", "Jul");
		tmpStr = tmpStr.replaceAll( "중", "Jung");
		tmpStr = tmpStr.replaceAll( "쥐", "Jwi");
		tmpStr = tmpStr.replaceAll( "즈", "Jeu");
		tmpStr = tmpStr.replaceAll( "즉", "Jeuk");
		tmpStr = tmpStr.replaceAll( "즐", "Jeul");
		tmpStr = tmpStr.replaceAll( "즘", "Jeum");
		tmpStr = tmpStr.replaceAll( "즙", "Jeup");
		tmpStr = tmpStr.replaceAll( "증", "Jeung");
		tmpStr = tmpStr.replaceAll( "지", "Ji");
		tmpStr = tmpStr.replaceAll( "직", "Jik");
		tmpStr = tmpStr.replaceAll( "진", "Jin");
		tmpStr = tmpStr.replaceAll( "질", "Jil");
		tmpStr = tmpStr.replaceAll( "짐", "Jim");
		tmpStr = tmpStr.replaceAll( "집", "Jip");
		tmpStr = tmpStr.replaceAll( "징", "Jing");
		tmpStr = tmpStr.replaceAll( "짜", "Jja");
		tmpStr = tmpStr.replaceAll( "째", "Jjae");
		tmpStr = tmpStr.replaceAll( "쪼", "Jjo");
		tmpStr = tmpStr.replaceAll( "찌", "Jji");
		tmpStr = tmpStr.replaceAll( "차", "Cha");
		tmpStr = tmpStr.replaceAll( "착", "Chak");
		tmpStr = tmpStr.replaceAll( "찬", "Chan");
		tmpStr = tmpStr.replaceAll( "찰", "Chal");
		tmpStr = tmpStr.replaceAll( "참", "Cham");
		tmpStr = tmpStr.replaceAll( "창", "Chang");
		tmpStr = tmpStr.replaceAll( "채", "Chae");
		tmpStr = tmpStr.replaceAll( "책", "Chaek");
		tmpStr = tmpStr.replaceAll( "처", "Cheo");
		tmpStr = tmpStr.replaceAll( "척", "Cheok");
		tmpStr = tmpStr.replaceAll( "천", "Cheon");
		tmpStr = tmpStr.replaceAll( "철", "Cheol");
		tmpStr = tmpStr.replaceAll( "첨", "Cheom");
		tmpStr = tmpStr.replaceAll( "첩", "Cheop");
		tmpStr = tmpStr.replaceAll( "청", "Cheong");
		tmpStr = tmpStr.replaceAll( "체", "Che");
		tmpStr = tmpStr.replaceAll( "초", "Cho");
		tmpStr = tmpStr.replaceAll( "촉", "Chok");
		tmpStr = tmpStr.replaceAll( "촌", "Chon");
		tmpStr = tmpStr.replaceAll( "총", "Chong");
		tmpStr = tmpStr.replaceAll( "최", "Choe");
		tmpStr = tmpStr.replaceAll( "추", "Chu");
		tmpStr = tmpStr.replaceAll( "취", "Chwi");
		tmpStr = tmpStr.replaceAll( "축", "Chuk");
		tmpStr = tmpStr.replaceAll( "춘", "Chun");
		tmpStr = tmpStr.replaceAll( "출", "Chul");
		tmpStr = tmpStr.replaceAll( "춤", "Chum");
		tmpStr = tmpStr.replaceAll( "충", "Chung");
		tmpStr = tmpStr.replaceAll( "측", "Cheuk");
		tmpStr = tmpStr.replaceAll( "층", "Cheung");
		tmpStr = tmpStr.replaceAll( "치", "Chi");
		tmpStr = tmpStr.replaceAll( "칙", "Chik");
		tmpStr = tmpStr.replaceAll( "친", "Chin");
		tmpStr = tmpStr.replaceAll( "칠", "Chil");
		tmpStr = tmpStr.replaceAll( "침", "Chim");
		tmpStr = tmpStr.replaceAll( "칩", "Chip");
		tmpStr = tmpStr.replaceAll( "칭", "Ching");
		tmpStr = tmpStr.replaceAll( "카", "Ka");
		tmpStr = tmpStr.replaceAll( "코", "Ko");
		tmpStr = tmpStr.replaceAll( "쾌", "Kwae");
		tmpStr = tmpStr.replaceAll( "크", "Keu");
		tmpStr = tmpStr.replaceAll( "큰", "Keun");
		tmpStr = tmpStr.replaceAll( "키", "Ki");
		tmpStr = tmpStr.replaceAll( "타", "Ta");
		tmpStr = tmpStr.replaceAll( "탁", "Tak");
		tmpStr = tmpStr.replaceAll( "탄", "Tan");
		tmpStr = tmpStr.replaceAll( "탈", "Tal");
		tmpStr = tmpStr.replaceAll( "탐", "Tam");
		tmpStr = tmpStr.replaceAll( "탑", "Tap");
		tmpStr = tmpStr.replaceAll( "탕", "Tang");
		tmpStr = tmpStr.replaceAll( "태", "Tae");
		tmpStr = tmpStr.replaceAll( "택", "Taek");
		tmpStr = tmpStr.replaceAll( "탬", "Taem");
		tmpStr = tmpStr.replaceAll( "탱", "Taeng");
		tmpStr = tmpStr.replaceAll( "터", "Teo");
		tmpStr = tmpStr.replaceAll( "테", "Te");
		tmpStr = tmpStr.replaceAll( "템", "Tem");
		tmpStr = tmpStr.replaceAll( "토", "To");
		tmpStr = tmpStr.replaceAll( "톤", "Ton");
		tmpStr = tmpStr.replaceAll( "톨", "Tol");
		tmpStr = tmpStr.replaceAll( "통", "Tong");
		tmpStr = tmpStr.replaceAll( "퇴", "Toe");
		tmpStr = tmpStr.replaceAll( "투", "Tu");
		tmpStr = tmpStr.replaceAll( "퉁", "Tung");
		tmpStr = tmpStr.replaceAll( "튀", "Twi");
		tmpStr = tmpStr.replaceAll( "트", "Teu");
		tmpStr = tmpStr.replaceAll( "특", "Teuk");
		tmpStr = tmpStr.replaceAll( "틈", "Teum");
		tmpStr = tmpStr.replaceAll( "티", "Ti");
		tmpStr = tmpStr.replaceAll( "파", "Pa");
		tmpStr = tmpStr.replaceAll( "판", "Pan");
		tmpStr = tmpStr.replaceAll( "팔", "Pal");
		tmpStr = tmpStr.replaceAll( "패", "Pae");
		tmpStr = tmpStr.replaceAll( "팽", "Paeng");
		tmpStr = tmpStr.replaceAll( "퍼", "Peo");
		tmpStr = tmpStr.replaceAll( "페", "Pe");
		tmpStr = tmpStr.replaceAll( "펴", "Pyeo");
		tmpStr = tmpStr.replaceAll( "편", "Pyeon");
		tmpStr = tmpStr.replaceAll( "폄", "Pyeom");
		tmpStr = tmpStr.replaceAll( "평", "Pyeong");
		tmpStr = tmpStr.replaceAll( "폐", "Pye");
		tmpStr = tmpStr.replaceAll( "포", "Po");
		tmpStr = tmpStr.replaceAll( "폭", "Pok");
		tmpStr = tmpStr.replaceAll( "표", "Pyo");
		tmpStr = tmpStr.replaceAll( "푸", "Pu");
		tmpStr = tmpStr.replaceAll( "품", "Pum");
		tmpStr = tmpStr.replaceAll( "풍", "Pung");
		tmpStr = tmpStr.replaceAll( "프", "Peu");
		tmpStr = tmpStr.replaceAll( "피", "Pi");
		tmpStr = tmpStr.replaceAll( "픽", "Pik");
		tmpStr = tmpStr.replaceAll( "필", "Pil");
		tmpStr = tmpStr.replaceAll( "핍", "Pip");
		tmpStr = tmpStr.replaceAll( "하", "Ha");
		tmpStr = tmpStr.replaceAll( "학", "Hak");
		tmpStr = tmpStr.replaceAll( "한", "Han");
		tmpStr = tmpStr.replaceAll( "할", "Hal");
		tmpStr = tmpStr.replaceAll( "함", "Ham");
		tmpStr = tmpStr.replaceAll( "합", "Hap");
		tmpStr = tmpStr.replaceAll( "항", "Hang");
		tmpStr = tmpStr.replaceAll( "해", "Hae");
		tmpStr = tmpStr.replaceAll( "핵", "Haek");
		tmpStr = tmpStr.replaceAll( "행", "Haeng");
		tmpStr = tmpStr.replaceAll( "향", "Hyang");
		tmpStr = tmpStr.replaceAll( "허", "Heo");
		tmpStr = tmpStr.replaceAll( "헌", "Heon");
		tmpStr = tmpStr.replaceAll( "험", "Heom");
		tmpStr = tmpStr.replaceAll( "헤", "He");
		tmpStr = tmpStr.replaceAll( "혀", "Hyeo");
		tmpStr = tmpStr.replaceAll( "혁", "Hyeok");
		tmpStr = tmpStr.replaceAll( "현", "Hyeon");
		tmpStr = tmpStr.replaceAll( "혈", "Hyeol");
		tmpStr = tmpStr.replaceAll( "혐", "Hyeom");
		tmpStr = tmpStr.replaceAll( "협", "Hyeop");
		tmpStr = tmpStr.replaceAll( "형", "Hyeong");
		tmpStr = tmpStr.replaceAll( "혜", "Hye");
		tmpStr = tmpStr.replaceAll( "호", "Ho");
		tmpStr = tmpStr.replaceAll( "혹", "Hok");
		tmpStr = tmpStr.replaceAll( "혼", "Hon");
		tmpStr = tmpStr.replaceAll( "홀", "Hol");
		tmpStr = tmpStr.replaceAll( "홉", "Hop");
		tmpStr = tmpStr.replaceAll( "홍", "Hong");
		tmpStr = tmpStr.replaceAll( "화", "Hwa");
		tmpStr = tmpStr.replaceAll( "확", "Hwak");
		tmpStr = tmpStr.replaceAll( "환", "Hwan");
		tmpStr = tmpStr.replaceAll( "활", "Hwal");
		tmpStr = tmpStr.replaceAll( "황", "Hwang");
		tmpStr = tmpStr.replaceAll( "홰", "Hwae");
		tmpStr = tmpStr.replaceAll( "횃", "Hwaet");
		tmpStr = tmpStr.replaceAll( "회", "Hoe");
		tmpStr = tmpStr.replaceAll( "획", "Hoek");
		tmpStr = tmpStr.replaceAll( "횡", "Hoeng");
		tmpStr = tmpStr.replaceAll( "효", "Hyo");
		tmpStr = tmpStr.replaceAll( "후", "Hu");
		tmpStr = tmpStr.replaceAll( "훈", "Hun");
		tmpStr = tmpStr.replaceAll( "훤", "Hwon");
		tmpStr = tmpStr.replaceAll( "훼", "Hwe");
		tmpStr = tmpStr.replaceAll( "휘", "Hwi");
		tmpStr = tmpStr.replaceAll( "휴", "Hyu");
		tmpStr = tmpStr.replaceAll( "휼", "Hyul");
		tmpStr = tmpStr.replaceAll( "흉", "Hyung");
		tmpStr = tmpStr.replaceAll( "흐", "Heu");
		tmpStr = tmpStr.replaceAll( "흑", "Heuk");
		tmpStr = tmpStr.replaceAll( "흔", "Heun");
		tmpStr = tmpStr.replaceAll( "흘", "Heul");
		tmpStr = tmpStr.replaceAll( "흠", "Heum");
		tmpStr = tmpStr.replaceAll( "흡", "Heup");
		tmpStr = tmpStr.replaceAll( "흥", "Heung");
		tmpStr = tmpStr.replaceAll( "희", "Hui");
		tmpStr = tmpStr.replaceAll( "흰", "Huin");
		tmpStr = tmpStr.replaceAll( "히", "Hi");
		tmpStr = tmpStr.replaceAll( "힘", "Him");
		tmpStr = tmpStr.replaceAll( "  ", " ");
		tmpStr = tmpStr.replaceAll( " ", "_");

		return tmpStr;
	}

	public synchronized final static String getBinaryString(String str) {
		String rtnValue = "";

		char[] stringArray = str.toCharArray();
		for (int i = 0; i < stringArray.length; i++) {
			rtnValue += Integer.toBinaryString(stringArray[i]);
		}
		

		return rtnValue;
	}

	
	/**
	 * 구분자로 구별된 마지막 단어를 가져온다.
	 * @param str
	 * @return
	 */
	public synchronized final static String getStringOfLastSper(String str, String sper) {
	//	String rtnValue = "";
		if(str == null || str.length() == 0) return "";
		
		String arry[] = getStringArray(str, sper);
		

		return arry[arry.length-1];
	}
	
	/**
	 * 특정문자열부터 뒤에서부터 자른다. 예: jobMemberTriger 문자열에서 Triger 문자열 제거-> jobMember
	 * @param src
	 * @param indexStr
	 * @return
	 */
	public synchronized final static String getStringLastCut(String src, String  indexStr) {
		
		return src.substring(0, src.lastIndexOf(indexStr));
		
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("1111111111111111111");
	//	String  str ="icignal.batch.b2c.repository.B2CMapper.findOrderProdDailySummary";
	//	String  str ="mrt.afd,mrt.sdfaml,mrt.sefs";
	//	System.out.println( str.length());
	//    System.out.println(getStringOfLastSper(str, "."));
		
	    
	    
//	    List<String> items = Arrays.asList(str.split("\\s*,\\s*"));
	 /*   List<String> items = Arrays.asList(str.split(","));
		for(String item : items) {
			System.out.println("item:" + item );
			
		}*/
		
		System.out.println(getStringLastCut("jobMemberTrigger", "Trigger"));
		
		
	}





}
