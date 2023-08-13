#######################################################################
# FileName: treebase.py                                               #
# Programmer: Kate Kowalyshyn                                         #
# Created On: 7/25/2023                                               #
# Last Mondified: 8/13/2023                                           #
# Version: 2.0                                                        #
# Description: Front end for SQLite database for CRUD functionalities #
#######################################################################

import tkinter as tk
from tkinter import *
from tkinter import ttk
from tkinter import messagebox
from PIL import ImageTk, Image
from tkinter import filedialog
from tkinter.filedialog import askopenfile
from io import BytesIO
import sqlite3
from photopreviewdialog import PhotoDialog
from logindialog import LoginDialog


root = Tk()
root.title('Destinations Database')

root.geometry("1000x700")


# connect to database & create cursor
conn = sqlite3.connect('slideshow.db')
c = conn.cursor()

# Create Table
c.execute("""CREATE TABLE if not exists Destinations (
    id integer,
	name text,
	desscription text,
	category text,
	photo blob )
	""")

# Commit changes & close connection
conn.commit()
conn.close()

def query_database():
	# Connect to database and create cursor
	conn = sqlite3.connect('slideshow.db')
	c = conn.cursor()

	c.execute("SELECT id, name, description, category FROM Destinations")
	records = c.fetchall()
	
	# Add our data to the screen
	global count
	count = 0
	

	for record in records:
		if count % 2 == 0:
			my_tree.insert(parent='', index='end', iid=count, text='', values=(record[0], record[1], record[2], record[3]), tags=('evenrow',))
		else:
			my_tree.insert(parent='', index='end', iid=count, text='', values=(record[0], record[1], record[2], record[3]), tags=('oddrow',))
		# increment counter
		count += 1

	# Commit changes & close connection
	conn.commit()
	conn.close()

def upload_photo():
    global filename,img
    f_types=[('All files','*.*'),
              ('JPG','*.jpg'),
              ('PNG','*.png')]
    filename=filedialog.askopenfilename(filetypes=f_types)
    img=Image.open(filename)
    img_resized=img.resize((320,180))
    img=ImageTk.PhotoImage(img_resized)
    
    photo_preview.config(image=img)


# Add Style & pick theme
style = ttk.Style()
style.theme_use('clam')

# Configure the Treeview Colors
style.configure("Treeview",
	rowheight=25)

# Change Selected Color
style.map('Treeview',
	background=[('selected', "#347083")])
 

# Create a Treeview Frame
tree_frame = Frame(root)
tree_frame.pack(pady=10)

# Create a Treeview Scrollbar
tree_scroll = Scrollbar(tree_frame)
tree_scroll.pack(side=RIGHT, fill=Y)

# Create The Treeview
my_tree = ttk.Treeview(tree_frame, yscrollcommand=tree_scroll.set, selectmode="extended")
my_tree.pack()

# Configure the Scrollbar
tree_scroll.config(command=my_tree.yview)

# Define Our Columns
my_tree['columns'] = ("ID", "Name", "Description", "Category")#, "Photo")

# Format Our Columns
my_tree.column("#0", width=0, stretch=NO)
my_tree.column("ID", anchor=CENTER, width=30)
my_tree.column("Name", anchor=CENTER, width=200)
my_tree.column("Description", anchor=CENTER, width=600)
my_tree.column("Category", anchor=CENTER, width=100)
# my_tree.column("Photo", anchor=CENTER, width=400)


# Create Headings
my_tree.heading("#0", text="", anchor=W)
my_tree.heading("ID", text="ID", anchor=CENTER)
my_tree.heading("Name", text="Name", anchor=W)
my_tree.heading("Description", text="Desscription", anchor=W)
my_tree.heading("Category", text="Category", anchor=CENTER)
# my_tree.heading("Photo", text="Photo", anchor=CENTER)


# Create Striped Row Tags
my_tree.tag_configure('oddrow', background="white")
my_tree.tag_configure('evenrow', background="lightblue")


# Add Record Entry Boxes
data_frame = LabelFrame(root, text="Destination")
data_frame.pack(fill="x", expand="yes", padx=20)

id_label = Label(data_frame, text="ID")
id_label.grid(row=0, column=0, padx=10, pady=10)
id_entry = Entry(data_frame)
id_entry.grid(row=0, column=1, padx=10, pady=10)

name_label = Label(data_frame, text="Name")
name_label.grid(row=0, column=2, padx=10, pady=10)
name_entry = Entry(data_frame)
name_entry.grid(row=0, column=3, padx=10, pady=10)

desc_label = Label(data_frame, text="Description")
desc_label.grid(row=0, column=4, padx=10, pady=10)
desc_entry = Entry(data_frame)
desc_entry.grid(row=0, column=5, padx=10, pady=10)

category_label = Label(data_frame, text="Category")
category_label.grid(row=0, column=6, padx=10, pady=10)
location_types = ['City','Countryside','Desert','Island','Mountain']
category_entry = ttk.Combobox(data_frame, values=location_types, width=13)
category_entry.grid(row=0, column=7, padx=10, pady=10)

photo_label = Label(data_frame, text="Photo")
photo_label.grid(row=0, column=8, padx=10, pady=10)
photo_entry = Button(data_frame, text = "Upload", command= upload_photo)
photo_entry.grid(row=0, column=9, padx=10, pady=10)

photo_preview = Label(data_frame, text='')
photo_preview.grid(row=1, column=3, columnspan=3,padx=10, pady=10)

    

# Move Row Up
def up():
	rows = my_tree.selection()
	for row in rows:
		my_tree.move(row, my_tree.parent(row), my_tree.index(row)-1)

# Move Rown Down
def down():
	rows = my_tree.selection()
	for row in reversed(rows):
		my_tree.move(row, my_tree.parent(row), my_tree.index(row)+1)

# Remove one record
def remove_one():
	x = my_tree.selection()[0]
	id = my_tree.set(x)
	id = id['ID']
	my_tree.delete(x)

	# Connect database & create cursor
	conn = sqlite3.connect('slideshow.db')
	c = conn.cursor()

	# Delete From Database
	c.execute("DELETE from Destinations WHERE oid=" + id)
	
	# Commit changes & close connection
	conn.commit()
	conn.close()

	# Clear The Entry Boxes
	clear_entries()

	# Add a little message box for fun
	messagebox.showinfo("Deleted!", "Your Record Has Been Deleted!")


# Clear entry boxes
def clear_entries():
	# Clear entry boxes
	name_entry.delete(0, END)
	desc_entry.delete(0, END)
	id_entry.delete(0, END)
	category_entry.delete(0, END)
	photo_preview.config(image='')


# Select Record
def select_record(e):
	# Clear entry boxes
    name_entry.delete(0, END)
    desc_entry.delete(0, END)
    id_entry.delete(0, END)
    category_entry.delete(0, END)
    photo_preview.config(image='')

	# Grab record Number
    selected = my_tree.focus()
    
    # Grab record values
    values = my_tree.item(selected, 'values')

	# outputs to entry boxes
    name_entry.insert(0, values[0])
    desc_entry.insert(0, values[1])
    id_entry.insert(0, values[2])
    category_entry.insert(0, values[3])
    photo_entry.insert(0, values[4])

# Update record
def update_record():
	global img

	localImg = ImageTk.getimage(img)
	b = BytesIO()
	localImg.save(b, 'png')

	# Grab the record number
	selected = my_tree.focus()
	# Update record
	my_tree.item(selected, text="", values=(id_entry.get(), name_entry.get(), desc_entry.get(), category_entry.get()))

	# Update the database
	# connect to database & create cursor
	conn = sqlite3.connect('slideshow.db')
	c = conn.cursor()

	c.execute("""UPDATE Destinations SET
		name = :name,
		description = :desc,
		category = :category,
		picture = :photo
		WHERE id = :oid""",
		{
			'name': name_entry.get(),
			'desc': desc_entry.get(),
			'category': category_entry.get(),
			'photo': b.getvalue(),
			'oid': id_entry.get(),
		})
	
	# Commit changes & close connection
	conn.commit()
	conn.close()

	# Clear entry boxes
	clear_entries()
 
	# Add a little message box for fun
	messagebox.showinfo("Updated!", "Your Record Has Been Updated!")
 
	 
global filename, img

def view_photo():    
	# Get selected row
	x = my_tree.selection()[0]
	value = my_tree.set(x)
	id = value['ID']

    # Connect to database & create cursor
	conn = sqlite3.connect('slideshow.db')
	c = conn.cursor()

	result = c.execute("SELECT name, picture from Destinations where id = :id", (id,))
	result = result.fetchone()

	name = result[0]
	
	pic = result[1]
	pic = Image.open(BytesIO(pic))
	pic = ImageTk.PhotoImage(pic)

	dlg = PhotoDialog(name, pic)
	c.close()


# add new record to database
def add_record():
	global img
	# Update the database
	# Connect to database & create cursor
	conn = sqlite3.connect('slideshow.db')
	c = conn.cursor()

	#Convert image to save to database
	localImg = ImageTk.getimage(img)
	b = BytesIO()
	localImg.save(b, 'png')


	# Add New Record
	c.execute("INSERT INTO Destinations (id, name, description, category, picture) VALUES (:id, :name, :desc, :category, :photo)",
		{
			'id': id_entry.get(),
            'name': name_entry.get(),
			'desc': desc_entry.get(),
			'category': category_entry.get(),
			'photo': b.getvalue()
		})
	

	# Commit changes and close connection
	conn.commit()
	conn.close()

	# Clear entry boxes
	clear_entries()
	
	# Clear The Treeview Table
	my_tree.delete(*my_tree.get_children())

	# Run to pull data from database on start
	query_database()
	
 	# Add a little message box for fun
	messagebox.showinfo("Added!", "Your Record Has Been Added!")

# Add Buttons
button_frame = LabelFrame(root, text="Commands")
button_frame.pack(fill="x", expand="yes", padx=20)

update_button = Button(button_frame, text="Update Record", command=update_record)
update_button.grid(row=0, column=1, padx=27, pady=10)

add_button = Button(button_frame, text="Add Record", command=add_record)
add_button.grid(row=0, column=2, padx=27, pady=10)

remove_one_button = Button(button_frame, text="Remove Record", command=remove_one)
remove_one_button.grid(row=0, column=3, padx=27, pady=10)

view_img_button = Button(button_frame, text="View Photo", command=view_photo)
view_img_button.grid(row=0, column=4, padx=27, pady=10)

move_up_button = Button(button_frame, text="Move Up", command=up)
move_up_button.grid(row=0, column=5, padx=27, pady=10)

move_down_button = Button(button_frame, text="Move Down", command=down)
move_down_button.grid(row=0, column=6, padx=27, pady=10)

select_record_button = Button(button_frame, text="Clear Entry Boxes", command=clear_entries)
select_record_button.grid(row=0, column=7, padx=27, pady=10)


# Run to pull data from database on start
query_database()


dlg = LoginDialog(root)

root.mainloop()

