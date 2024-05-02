

def main():
    x = {
        'domain': 'irc.hackthissite.org',
        'subdomains': [
            {
                'domain': 'www.irc.hackthissite.org',
                'status': '200',
                'methods': "No-Methods",
                'ports': [
                    {
                        'service': 'http',
                        'banner': '',
                        'portNo': 80
                    },
                    {
                        'service': 'https',
                        'banner': '',
                        'portNo': 443
                    }
                ]
            },
            {
                'domain': 'irc.hackthissite.org',
                'status': '200',
                'methods': "No-Methods",
                'ports':
                    [
                        {'service': 'http', 'banner': '', 'portNo': 80}, {'service': 'https', 'banner': '', 'portNo': 443}, {'service': '', 'banner': 'ERROR :Closing Link: [157.46.223.142] (Throttled: Reconnecting too fast) - Email irc@hackthissite.org for more information.\r\n', 'portNo': 6667}, {'service': '', 'banner': 'ERROR :Closing Link: [157.46.223.142] (Throttled: Reconnecting too fast) - Email irc@hackthissite.org for more information.\r\n', 'portNo': 6697}, {'service': '', 'banner': 'ERROR :Closing Link: [157.46.223.142] (Throttled: Reconnecting too fast) - Email irc@hackthissite.org for more information.\r\n', 'portNo': 7000}, {'service': '', 'banner': 'ERROR :Closing Link: [157.46.223.142] (Throttled: Reconnecting too fast) - Email irc@hackthissite.org for more information.\r\n', 'portNo': 8068}, {'service': '', 'banner': 'ERROR :Closing Link: [157.46.223.142] (Throttled: Reconnecting too fast) - Email irc@hackthissite.org for more information.\r\n', 'portNo': 8080}]}, {'domain': 'wolf.irc.hackthissite.org', 'status': '200', 'methods': None, 'ports': [{'service': '', 'banner': '', 'portNo': 7000}]}, {'domain': 'lille.irc.hackthissite.org', 'status': '200', 'methods': None, 'ports': [{'service': 'https', 'banner': '', 'portNo': 443}]}]}

    return x