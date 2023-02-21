from datetime import datetime

from flask import Flask

app = Flask(__name__)

@app.route('/')
def index():
    current_time = datetime.now().strftime("%H:%M:%S")
    return f'<h1>Current time: {current_time}</h1>'

@app.route('/links')
def links():
    body = '''<a href='http://www.youtube.pl' target="_blank"Youtube</a> <br />
    It's the website of Youtube</a>
    <a href='http://www.pwr.edu.pl' target="_blank"PWr</a> <br />
    It's the website of PWr</a>
    '''
    return body

if __name__ == '__main__':
    app.run(debug=True)

