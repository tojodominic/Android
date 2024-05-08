import socket

class PortTCP:
    def __init__(self,host):
        self.target=host
        self.ports = list(range(1,1000))
        self.discovered_ports = []

    def scanport(self):
        while len(self.ports):
            port = self.ports.pop(0)
            try:
                sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
                sock.settimeout(2)
                sock.connect((self.target,port))
                sock.close()
                self.discovered_ports.append(port)
            except:
                # if the subdomain does not exist, just pass, print nothing
                pass
            finally:
                # Close the socket
                sock.close()
                pass