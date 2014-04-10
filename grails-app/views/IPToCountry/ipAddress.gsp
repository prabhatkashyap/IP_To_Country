<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:form controller="IPToCountry" action="getCountry">
    <div>
        Enter IP Address <g:textField name="ip"/>
        <g:submitButton name="Check Country"/>
    </div>
</g:form>
<g:if test="${!index}">
    <g:if test="${ipToCountry}">
        <table>
            <tr>
                <td>Country Name</td>
                <td>${ipToCountry?.countryName}</td>
            </tr>
            <tr>
                <td>Country Code (Format 2Digit)</td>
                <td>${ipToCountry?.countryCode2}</td>
            </tr>
            <tr>
                <td>Country Code (Format 3Digit)</td>
                <td>${ipToCountry?.countryCode3}</td>
            </tr>
        </table>
    </g:if>
    <g:else>
        No Country found with this IP
    </g:else>
</g:if>
</body>
</html>