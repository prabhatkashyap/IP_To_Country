import grails.transaction.Transactional
import ip_to_country.IPToCountry

import javax.servlet.http.HttpServletRequest

@Transactional
class IPAddressToCountryService {
    static transactional = false

    def insertDataFromFile(List<IPToCountry> list) {
        list*.save()
    }

    static String getUserIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    static Long getCountryFromIP(String ipAddress) {
        String[] token = ipAddress.tokenize(".")
        Long ipNumber = 0
        println("------length---------" + token.length)
        if (token.length == 4) {
            Double addressPart1 = token[0].trim() as Long
            Double addressPart2 = token[1].trim() as Long
            Double addressPart3 = token[2].trim() as Long
            Double addressPart4 = token[3].trim() as Long
            ipNumber = addressPart1 * 16777216 + addressPart2 * 65536 + addressPart3 * 256 + addressPart4
        }
        return ipNumber
    }
}
