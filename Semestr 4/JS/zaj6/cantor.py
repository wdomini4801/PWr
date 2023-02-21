from flask import Flask, request, url_for, redirect
import os

app = Flask(__name__)

@app.route('/')
def index():
    
    menu = f'''
        Go <a href="{url_for('exchange')}">here</a> to change money
        To exchange 50 CHF go <a href="{url_for('cantor', currency='CHF',
        amount=50, _external=True)}">here</a>
        <img src="{url_for('static', filename='Red_Apple.jpg')}"<br>
        '''
    
    return f'<h1>Hello World!!!!!!!!!!</h1><br>{menu}'

@app.route('/cantor/<string:currency>/<int:amount>')
def cantor(currency, amount):
    message = f'<h1>You selected {currency} and amount {amount}</h1>'
    return message

@app.route('/exchange', methods = ['GET', 'POST'])
def exchange():

    if request.method == 'GET':

        body = f'''
            <form id="exchange_form" action="{ url_for('exchange') }" method='POST'>
            <label for="currency">Currency</label>
            <input type='text' id="currency" name="currency" values="EUR"<br>
            <label for="amount">Amount</label>
            <input type="text" id="amount" name="amount" values="100"<br>
            <input type="submit" values="Send">
            </form>
        '''
        return body

    else:
    
        currency = 'EUR'
        if 'currency' in request.form:
            currency = request.form['currency']

        amount = 100
        if 'amount' in request.form:
            amount = request.form['amount']

        body = f'You want to exchange {amount} {currency}'

        return redirect(url_for('cantor', currency=currency, amount=amount))

@app.route('/about')
def about():
    return '<h1>Python</h1>'

if __name__ == '__main__':
    app.run(debug=True)