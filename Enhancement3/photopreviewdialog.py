###########################################################################
# FileName: photopreviewdialog.py                                         #
# Programmer: Kate Kowalyshyn                                             #
# Created On: 7/25/2023                                                   #
# Last Mondified: 8/11/2023                                               #
# Version: 2.0                                                            #
# Description: Class for main python program, to open photo from database #
###########################################################################

import tkinter

class PhotoDialog(object):

    root = None
    image = None

    def __init__(self, name, pic):
        self.image = pic

        #Create Window for viewing image
        tki = tkinter
        self.top = tki.Toplevel(PhotoDialog.root)

        #Create the frame for the image
        frm = tki.Frame(self.top, borderwidth=4, relief='ridge')
        frm.pack(fill='both', expand=True)

        #Create title, and pull from database to show with image
        lblTitle = tki.Label(frm, text=name)
        lblTitle.config(font=('Helvatica bold', 25))
        lblTitle.pack()

        #Pull image from database for viewing
        label = tki.Label(frm, image=self.image)
        label.image = self.image
        label.pack()

