package ip_to_country

import org.springframework.web.multipart.commons.CommonsMultipartFile

import javax.sql.CommonDataSource

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

    def deleteCSV() {
        IPToCountry.list().each {
            it.delete()
        }
        println("---------------dddd-------------" + IPToCountry.count)
        if (!IPToCountry.count) {
            render "success"
        } else {
            render("fail")
        }
    }

    def uploadcsv() {
        render(view: '/IPToCountry/uploadcsv')
    }

    def importcsv() {
//        println("-------pramsasa----" + request.getFile('file').size())
        CommonsMultipartFile commonsMultipartFile = params.file as CommonsMultipartFile
        byte[] list1 = commonsMultipartFile.bytes
        File file = File.createTempFile("tmp", ".dat")
        file.deleteOnExit()
        FileOutputStream fileOutputStream = new FileOutputStream(file)
        fileOutputStream.write(list1)
        fileOutputStream.flush();
        fileOutputStream.close();
        List<String> list = file.readLines()
        int tot = list.size()
        int p = 0
        int n = (list.size() / 5000)
        (0..n).eachWithIndex { int num, int i ->
            if (i == n) {
                List<IPToCountry> countries = []
                list.subList(p, tot).each { String line ->
                    String[] token = line.split(",")
                    IPToCountry ipToCountry = new IPToCountry()
                    ipToCountry.ipFrom = token[0] as Double
                    ipToCountry.ipTo = token[1] as Double
                    ipToCountry.countryCode2 = token[2].trim()
                    ipToCountry.countryCode3 = token[3].trim()
                    ipToCountry.countryName = token.length > 5 ? token[4] + "," + token[5] : token[4]
                    countries << ipToCountry
                }
                IPAddressToCountryService.insertDataFromFile(countries)
                println("-----------------successfull-----${i}---------------------")

            } else {
                List<IPToCountry> countries = []
                list.subList(p, p + 4999).each { String line ->
                    String[] token = line.split(",")
                    IPToCountry ipToCountry = new IPToCountry()
                    ipToCountry.ipFrom = token[0] as Double
                    ipToCountry.ipTo = token[1] as Double
                    ipToCountry.countryCode2 = token[2].trim()
                    ipToCountry.countryCode3 = token[3].trim()
                    ipToCountry.countryName = token.length > 5 ? token[4] + "," + token[5] : token[4]
                    countries << ipToCountry
                }
                IPAddressToCountryService.insertDataFromFile(countries)
                println("-----------------successfull--------${i}------------------")
            }
            p = p + 5000
        }
        println("-------------totl-----------" + IPToCountry.count)
    }
}
