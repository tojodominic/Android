from PortTCP import *
import threading

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



def main(portList):
    output=[]
    for i in range(portList.size()):
        hostOp={}
        op = PortEnum(portList.get(i))
        hostOp["ip"]=portList.get(i)
        ports=[]
        for p in op:
            det = {}
            banner = bannerEnum(portList.get(i),p)
            service = serviceEnum(p)
            det["service"] = service
            det["banner"] = banner
            det["portNo"] = p
            ports.append(det)
        hostOp["ports"]=ports
        output.append(hostOp)


    return output