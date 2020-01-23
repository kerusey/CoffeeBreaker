from http.server import BaseHTTPRequestHandler, HTTPServer # python3

class HandleRequests(BaseHTTPRequestHandler):
    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        self._set_headers()
        self.wfile.write("received get request")
        
    def do_POST(self):
        '''Reads post request body'''
        self._set_headers()
        content_len = int(self.headers.getheader('content-length', 0))
        post_body = self.rfile.read(content_len)
        self.wfile.write("received post request:<br>{}".format(post_body))

    def do_PUT(self):
        self.do_POST()

host = ''
port = 80
HTTPServer((host, port), HandleRequests).serve_forever()

'''
$ curl http://localhost/
received get request%                                                                                                                                                                                       

$ curl -X POST http://localhost/
received post request:<br>%                                                                                                                                                                                 

$ curl -X PUT http://localhost/
received post request:<br>%                                                                                                                                                                                 

$ echo 'hello world' | curl --data-binary @- http://localhost/
received post request:<br>hello world
'''
