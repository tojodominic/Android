import requests
from bs4 import BeautifulSoup

class DirectoryList:

    def __init__(self,protocol,host):
        self.directory=['admin', 'download', 'downloads', 'image', 'pages', 'search', 'security', 'sources', 'aboutus', 'administration', 'administrator', 'api', 'auth', 'backup', 'careers', 'cgi', 'cgi-bin', 'cgi-home', 'cgi-local', 'cgi-sys', 'data', 'database', 'databases', 'db', 'dir', 'directory', 'file', 'history', 'index', 'index1', 'index2', 'index3', 'lib', 'libraries', 'library', 'my', 'myaccount','new', 'news', 'online', 'php', 'phpMyAdmin', 'phpmyadmin', 'phpinfo', 'pic', 'pics', 'pictures', 'research', 'resource', 'resources', 'src', 'script', 'scripts', 'search', 'signin', 'signup', 'simple', 'single', 'site-map', 'site_map', 'sitemap', 'sites', 'sysadmin', 'system', 'upload', 'uploads', 'webadmin', 'webapp', 'webboard', 'wp', 'wp-admin', 'wp-content', 'wp-includes', 'wp-login', 'wp-register']
        self.protocol=protocol
        self.host=host
        self.output_directory=[]

    def scan(self):
        headers={"User-Agent": "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"}
        dir = self.directory
        while(len(dir)):
            i=dir.pop(0)
            url=f"{self.protocol}://{self.host}/{i}/"
            domain_p=url
            try:
                response = requests.get(url,timeout=5)
                output = {}
                #print(url+" --> "+str(response.status_code))
                if response.status_code!=404:
                    output["path"]=url
                    output["status"]=str(response.status_code)
                    finalurlOp=[]
                    if response.status_code==200:
                        #output["urls"]=fetch()
                        #fetching urls
                        outputurl=[]
                        #anchor tab scarpping...
                        soup = BeautifulSoup(response.text,'html.parser')
                        for a in soup.find_all('a', href=True):
                            urls_href = a['href']
                            if urls_href.startswith("/"):
                                #print(domain_p+urls_href)
                                outputurl.append(domain_p+urls_href)
                            elif urls_href.startswith("#"):
                                pass
                            else:
                                if domain_p in urls_href:
                                    #print(urls_href)
                                    outputurl.append(urls_href)
                        #scrpping img tags....
                        for b in soup.find_all('img', src=True):
                            urls_src = b['src']
                            if urls_src.startswith("/"):
                                #print(domain_p+urls_src)
                                outputurl.append(domain_p+urls_src)
                            elif urls_src.startswith("#"):
                                pass
                            else:
                                if domain_p in urls_src:
                                    #print(urls_src)
                                    outputurl.append(urls_src)
                        #scrapping script tags...
                        for c in soup.find_all('script', src=True):
                            script_src = c['src']
                            if script_src.startswith("/"):
                                #print(domain_p+script_src)
                                outputurl.append(domain_p+script_src)
                            elif script_src.startswith("#"):
                                pass
                            else:
                                if domain_p in script_src:
                                    #print(script_src)
                                    outputurl.append(script_src)

                        #removing duplicates
                        finalurlOp=set(outputurl)

                    #saving to dict...
                    output["urls"]=list(finalurlOp)
                    self.output_directory.append(output)
            except Exception as e:
                #print(e)
                pass