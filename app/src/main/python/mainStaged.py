from asyncio import protocols
from SubDomain import *
from PortTCP import *
from urllib.parse import urlsplit
import threading
import whois
from Wappalyzer import Wappalyzer, WebPage
from DirectoryList import *
import socket

output = {}


def extractFe(userInput):
    #extracting the domain name without protocols...
    base_url = "{0.netloc}".format(urlsplit(userInput))
    if base_url=="":
        base_url=userInput

    if "http://" in userInput:
        return "http",base_url
    elif "https://" in userInput:
        return "https",base_url
    else:
        return "https",base_url

def dns_lookup(domain):
    op = []
    try:
        # Resolve domain name to IP address
        ip_address = socket.gethostbyname(domain)
        op.append(["IP Address : ",ip_address])
        canonical_name = socket.gethostbyname_ex(domain)
        op.append(["CNAME : ", canonical_name])
        aliases = canonical_name[1]
        if aliases:
            op.append(["Aliases name : ", aliases])
        ip_addresses = canonical_name[2]
        if ip_addresses:
            op.append(["IP Addresses : ", ip_addresses])
        return op

    except socket.gaierror as e:
        return op



def PortEnum(host):
    ObjPortScn = PortTCP(host)


    ObjPortScn.thread_list=[]
    for t in range(100):
        thread=threading.Thread(target=ObjPortScn.scanport)
        ObjPortScn.thread_list.append(thread)
    for thread in ObjPortScn.thread_list:
        thread.start()
    for thread in ObjPortScn.thread_list:
        thread.join(1.0)

    return ObjPortScn.discovered_ports

def serviceEnum(port):
    #return "Port-Service"
    try:
        return socket.getservbyport(int(port),'tcp')
    except OSError:
        return ""

# def bannerEnum(port):
#     return "Port-Banner"

def bannerEnum(domain,port):
    if port in [53,80,6980,443]:
        return ""
    try:
        s=socket.socket()
        s.settimeout(2)
        s.connect((domain,int(port)))
        banner=s.recv(1024)
        s.shutdown(1) # By convention, but not actually necessary
        s.close()
        #print(banner.decode("utf-8"))
        return banner.decode("utf-8")
    except Exception as e:
        return ""

#directory listing...
def directoryEnum(protocol,host):
    objDir = DirectoryList(protocol,host)
    objDir.thread_list=[]
    for t in range(100):
        thread=threading.Thread(target=objDir.scan)
        objDir.thread_list.append(thread)
    for thread in objDir.thread_list:
        thread.start()
    for thread in objDir.thread_list:
        thread.join(1.0)
    return objDir.output_directory


#whois lookup....
def whoisEnum(domain):
    try:
        w = whois.whois(domain)
        return str(w)
    except Exception as e:
        return "Exception on whois"

#technology enumeration...
def techEnum(protocol,domain):
    try:
        url = f"{protocol}://{domain}"
        webpage = WebPage.new_from_url(url)
        wappalyzer = Wappalyzer.latest()
        #wappalyzer.analyze(webpage)
        return str(wappalyzer.analyze_with_versions_and_categories(webpage))
    except Exception as e:
        return "No-Data"

def status_method(listodofDomains):
    output_subdomains = []
    for i in listodofDomains:
        domain = i["domain"]
        protocol=i["Protocol"]
        try:
            response = requests.get(f"{protocol}://{domain}")
            responseOpt = requests.options(f"{protocol}://{domain}")
            output_subdomains.append([domain,str(response.status_code),responseOpt.raw.getheader('allow')])
        except:
            pass

    return output_subdomains


def main(userInput):
    ###print(extractFe(userInput))

    ret = []

    for i in range(userInput.size()):
        domains = {}
        proto,domainName = extractFe(userInput.get(i))
        domains["Protocol"]=proto
        domains["domain"]=domainName
        ret.append(domains)


    op = status_method(ret)

    output["domain"]=ret[0]["domain"]
    subDomainsList=[]

    for host in op:

        subInnerDomain={}
        subInnerDomain["domain"]=host[0]

        #storing the whois lookup...
        subInnerDomain["whois"]=whoisEnum(host[0])
        #DNS Enumeration...
        subInnerDomain["DNS"]=dns_lookup(host[0])
        #techology identifications...
        subInnerDomain["tech"]=techEnum(proto,host[0])
        #directory enumeration...
        subInnerDomain["directory"]=directoryEnum(proto,host[0])

        #port scanning
        portsFull=[]


        #Perform port scanning and return portNo,Service and Banner....
        ports=PortEnum(host[0])

        #print(ports)

        for port in ports:
            portDetials={}
            service = serviceEnum(port)
            banner = bannerEnum(host[0],port)
            portDetials["service"]=service
            portDetials["banner"]=banner
            portDetials["portNo"]=port

            portsFull.append(portDetials)


        #directory listing...

        subInnerDomain["status"]=host[1]
        subInnerDomain["methods"]=host[2]
        subInnerDomain["ports"]=portsFull


        #adding to subdomain list...
        subDomainsList.append(subInnerDomain)



    output["subdomains"]=subDomainsList


    return output