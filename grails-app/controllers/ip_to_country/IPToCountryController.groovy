package ip_to_country

class IPToCountryController {
    def IPAddressToCountryService

    def index() {

        render(view: 'ipAddress', model: [index: true])
    }

    def getCountry() {
//        String ip = IPAddressToCountryService.getUserIpAddress(request)
        String ip = params?.ip
        Long ipNumber = IPAddressToCountryService.getCountryFromIP(ip)
        IPToCountry ipToCountry = IPToCountry.findByIpFromLessThanEqualsAndIpToGreaterThanEquals(ipNumber, ipNumber)
        render(view: 'ipAddress', model: [ipToCountry: ipToCountry])
    }
}
