package com.tensquare.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn3mehyzTIs+30H636Rg4\n" +
					"Kti1ad83LAsAFQVdBWaP9V0JxFFC9G241/1plvStG8FvwSCrfE5ICVmyk1oBK/Zu\n" +
					"jUV/8ME+daN3sZtUCDhs5mW884eMMFvI9nScm+4SGjgz44QMzx/THSDyh2WZ93em\n" +
					"pBQx6QjWGzd/1J07QLwS7eTpbCjvO08eyeS1FKdhON7gT0ZjAy18ssiFoCWt3SM5\n" +
					"qVvJuN2mjYhfTeiHVywMzm/t7gmt+7Sm2coZV/dim9u0oDUsmTnro9DQbusWQLpZ\n" +
					"EkIWJEz6jy5QqOLgoLxGJ41mh3sO4+I7Lh34qzz46rmJ7tiKc+wMns2hdFnRsFF7\n" +
					"/wIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfeZ6HLNMiz7fQ\n" +
					"frfpGDgq2LVp3zcsCwAVBV0FZo/1XQnEUUL0bbjX/WmW9K0bwW/BIKt8TkgJWbKT\n" +
					"WgEr9m6NRX/wwT51o3exm1QIOGzmZbzzh4wwW8j2dJyb7hIaODPjhAzPH9MdIPKH\n" +
					"ZZn3d6akFDHpCNYbN3/UnTtAvBLt5OlsKO87Tx7J5LUUp2E43uBPRmMDLXyyyIWg\n" +
					"Ja3dIzmpW8m43aaNiF9N6IdXLAzOb+3uCa37tKbZyhlX92Kb27SgNSyZOeuj0NBu\n" +
					"6xZAulkSQhYkTPqPLlCo4uCgvEYnjWaHew7j4jsuHfirPPjquYnu2Ipz7AyezaF0\n" +
					"WdGwUXv/AgMBAAECggEAUZLRX02zdmQQH0siYc4stOYC7fPq8dFyqk4DBgcvbg+4\n" +
					"Nj5m5EbXvUv33eqsPb1Vn9FJRp14RagnKy5n2QgGBqddSVZfz6bdS/ErK8VJFxVF\n" +
					"cMCOGhJpGoANt8kwY08KY7+hGbWqtISs6kszVZ1TlSxrV0tY0bUy4xvGOle5ywt0\n" +
					"2KzUzxrvS6xoQdKz/k+B/Kci4FZqzfds5KqheqDdPqr18mIJEibp9xNqS8FvaO66\n" +
					"9U8JW3qTayYc+Aymh2wpaCh1Yoq7s/E+CsbnZnR1wU2Q4EY1aDNMnHX8c0Sig3qK\n" +
					"JDGd0CPy2tdGYba+Km9UMQN54A31xhgIHHUrzOif2QKBgQDTo+WWwK7ScSCX3ZvB\n" +
					"dWIAivivExUzv8EUXyP7opVsewq8M9TeDpcjeQX+7bn10+jie6X5z2N12mCSsJRk\n" +
					"OJDvKNngCaC4Sv3CKf9IORotGZf8Knyn2WS/lS3lndupTWM7XEOiK2CxFnuXVmSy\n" +
					"VOKInMbaxhh/CzzHxz8SBycfBQKBgQDA5qqoIuZC325k911SPrMdPWM/yjz+JXtj\n" +
					"oS9YrEZW8qSfXB4pfHnP/veNrggT86Va4cTAaRrGLgWKLkI4iiQ9Fx8z8sX8r+Gq\n" +
					"IYqZC2/34Gp81v2OihIEHaM2AGrzT/VD+W+JuefDJYYxJwwoyzU6TUiUH06GksP1\n" +
					"zEB2r3J2MwKBgFNJma2VEHeSu4/oT0Z36mRqTGmhqeNeoB5jPqblmcZCObxM7YWh\n" +
					"2krEct6bMWaTyX9vx6aivfKASScRRKSYOjlUK0HmqBA+utRxcJw8lJeRDeXX1oy7\n" +
					"mdFnZNOLJwnegaG3soocZgXq4iJxhllD3I6EnCE2eidtBbNdLu3qULP9AoGBALSS\n" +
					"OIYTxUAsLwbMl8r5bmmcoV+BTP8KzypAJDX5bg7OLFQQwxtKriyKG+nAU0d59/vP\n" +
					"A4Kp4KpLQlxLWSncBng5QLg8NiGH8tJze24wSqvao1QhXFEl5h98Lt/KHFOPMaRj\n" +
					"xolCQTVn1uoEB1nYfwcIeTxO4g8PK+jXPjDbo2YJAoGBAMMHpG3m04SoLN+kt4x0\n" +
					"lep6VcLWJs7Jb5DjaOPCupbfaLjug4rOgqHQcoWJ2B/YScghV+wO/RzatnXK0upO\n" +
					"Z5wejkY5h5T62Loow4mMmtr7qcgEWswSXkl3pTHfH1EVKi+fNrf1TVLs0Jvsrmmi\n" +
					"lxYdUH3yEAt7OEy5s0WrfoPg";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
