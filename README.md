IP_To_Country
=============

Getting Started , 

First of all you have to import a CSV present in plugin web-app/countrycsv/ip-to-country.csv.
to import csv , you need to do add few lines in Bootstrap.grooy.
      1)Declear (def IPAddressToCountryService).
      2)You need to add these lines in  def init block .
              if (IPToCountry.count == 0) {
            println("---------------------------started-------------------------")
            String path = '';
            path = ServletContextHolder.getServletContext().getRealPath("/")
            println("-------------web---root --------path----------------" + path)
            List<String> list = new File(path, "/countrycsv/ip-to-country.csv").readLines()
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
        }
        
        
        follow the steps and restart your application , if your application is using database migration plugin then             generate changelog because this plugin add a domain class to your application and when you restart your                 application , all the CSV content are inserted to your database in table ip_to_country.
        
