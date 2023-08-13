###########################################################################
# FileName: logindialog.py                                                #
# Programmer: Kate Kowalyshyn                                             #
# Created On: 8/13/2023                                                   #
# Last Mondified: 8/13/2023                                               #
# Version: 1.0                                                            #
# Description: Class for login screen to get access to the front end GUI  #
###########################################################################

# imports
from tkinter import *
import sqlite3
from hashlib import sha256


class LoginDialog(Frame):

    root = None
    image = None

    def __init__(self, parent):
        Frame.__init__(self)

        self.authenticated = False
        
        # Hide parent window so user must login
        self.parent = parent
        self.parent.withdraw()
        
        # Self close, if someone exits log in, the whole program will close
        self.top = Toplevel(LoginDialog.root)
        self.top.protocol('WM_DELETE_WINDOW', self.on_close)

        # Username label and entry box
        self.lblUserName = Label(self.top, text='User Name').grid(row=0, column=0)
        self.userName = StringVar()
        self.userNameEntry = Entry(self.top, textvariable=self.userName).grid(row=0, column=1)
        
        # Password label and entry box
        self.lblPassword = Label(self.top, text='Password').grid(row=1, column=0)
        self.password = StringVar()
        self.passwordEntry = Entry(self.top, textvariable=self.password, show='*').grid(row=1, column=1)

        # Submit button
        self.btnSubmit = Button(self.top, text='Submit', command=self.submit).grid(row=2, column=0, columnspan=2)

    # if not authenication, close parent application
    def on_close(self):
        if not self.authenticated:
            self.parent.destroy()

    def submit(self):
        # connect to database & create cursor
        conn = sqlite3.connect('slideshow.db')
        c = conn.cursor()

	    # Add New Record
        c.execute("SELECT password from users where username = :user", {'user' : self.userName.get()})
        records = c.fetchall()

        if len(records) == 1:
            print(records[0][0])

            # if sha256 hash matches one stored in database, account is authenticated
            if sha256(self.password.get().encode('utf-8')).hexdigest() == records[0][0]:
                self.authenticated = True
	

        conn.close()

        # if authenticated, open up database front end for CRUD functionalities
        if self.authenticated:
            self.parent.deiconify()
            self.top.destroy()


        



