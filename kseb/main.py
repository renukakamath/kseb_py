import database as db
from flask import Flask,render_template,request,redirect,url_for,session
import qrcode
import uuid
app=Flask(__name__)

app.secret_key="abcdefg"
from api import api
app.register_blueprint(api)
@app.route('/')
def home():
    # if "login_id" in session:
    #     res=db.select("select * from user")
    #     return render_template("home.html",data=res)
    return render_template("public/home.html")

@app.route('/login',methods=['get','post'])
def login():
    if "login" in request.form:
        username=request.form['username']
        password=request.form['password']
        res=db.select("select * from login where username='%s' and password='%s'"%(username,password))
        if(len(res)>0):
            session['login_type']=res[0]['login_type']
            session['login_id']=res[0]['login_id']
            print("ok")
            if session['login_type']=="admin":
                return redirect(url_for('admin_home'))
            elif session['login_type']=="employee":
                return redirect(url_for('employee_home'))
            # else:
            #     return redirect(url_for('consumer_home'))
        else:
            print("ok")
            return render_template("public/login.html",error_msg="failed")
    return render_template("public/login.html")





@app.route('/admin_view_complaint',methods=['get','post'])
def admin_view_complaint():
    if not session.get("login_id") is None:
        data={}
        q="select *,concat(f_name,' ',l_name) as NAME from complaints inner join consumers using(cons_id)"
        res=db.select(q)
        data['viewcomplaints']=res
        j=0
        for i in range(1,len(res)+1):
            if 'submit'+str(i) in request.form:
                reply=request.form['reply'+str(i)]
                print(res[j]['comp_id'])

                q="update complaints set status='%s' where comp_id='%s'"%(reply,res[j]['comp_id'])
                db.update(q)
                # flash("message send successfully")
                return redirect(url_for('admin_view_complaint'))
            j=j+1
        return render_template('admin/admin_view_complaint.html',data=res)
    else:
        return redirect(url_for('home'))



@app.route('/employee_add_consumer',methods=['get','post'])
def employee_add_consumer():
    if not session.get("login_id") is None:
        if "submit" in request.form:
            username=request.form['username']
            password=request.form['password']
            fname=request.form['f_name']
            lname=request.form['l_name']
            home=request.form['h_name']
            place=request.form['place']
            pincode=request.form['pincode']
            phone=request.form['phone']
            email=request.form['email']
            types=request.form['type']

            id1=db.insert("insert into login values(null,'%s','%s','consumers')"%(username,password))
            id=db.insert("insert into consumers values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s')"%(id1,fname,lname,home,place,pincode,phone,email,types))
            if(id>0):
                print("ok")
                path="static/qrcodes/"+str(id)+".png"
                img=qrcode.make(str(id))
                img.save(path)
                return redirect(url_for('view_consumer'))

            else:
                print("ok")
        return render_template("admin/employee_add_consumer.html")
    else:
        return redirect(url_for('home'))

@app.route('/view_consumer',methods=['get','post'])
def view_consumer():
    if not session.get("login_id") is None:
       if "delete" in request.args:
           id=request.args['id']
           res=db.delete("delete from consumers where cons_id='%s'" %(id))
           return redirect(url_for('view_consumer')) 
       res1=db.select("select * from consumers")
       print(res1)
       return render_template('admin/view_consumer.html',data=res1)
    else:
        return redirect(url_for('home'))

@app.route('/update_consumer',methods=['get','post'])
def update_consumer():
    if not session.get("login_id") is None:
        cons_id=request.args['id']
        if "update" in request.form:
            first=request.form['f_name']
            last=request.form['l_name']
            home=request.form['h_name']
            place=request.form['place']
            pincode=request.form['pincode']
            phone=request.form['phone']
            email=request.form['email']
            q="update consumers set f_name='%s',l_name='%s',h_name='%s',place='%s',pincode='%s',phone='%s',email='%s' where cons_id='%s'" % (first,last,home,place,pincode,phone,email,cons_id)
            print("ok")
            res=db.update(q)
            return redirect(url_for('view_consumer'))
        res=db.select("select * from consumers where cons_id='%s'" % (cons_id))
        return render_template('admin/update_consumer.html',data=res)
    else:
        return redirect(url_for('home'))

@app.route('/adminaddnotification',methods=['get','post'])
def adminaddnotification():
    if not session.get("login_id") is None:
        if 'Add' in request.form:
            types=request.form['type']
            desc=request.form['desc']
            id=db.insert("insert into notification values(null,'%s','%s',curdate())"%(types,desc))
            if(id>0):
                print("ok")
                return redirect(url_for('adminviewnotification'))

            else:
                print("ok")
        return render_template('admin/adminaddnotification.html')
    else:
        return redirect(url_for('home'))

@app.route('/adminviewnotification')
def adminviewnotification():
    if not session.get("login_id") is None:
        if "delete" in request.args:
           id=request.args['id']
           print(id)
           res=db.delete("delete from notification where noti_id='%s'" %(id))
           return redirect(url_for('adminviewnotification'))
        res=db.select("select * from notification")
        return render_template('admin/adminviewnotification.html',data=res)
    else:
        return redirect(url_for('home'))



@app.route('/adminaddtariff',methods=['get','post'])
def adminaddtariff():
    if not session.get("login_id") is None:
        if 'Add' in request.form:
            types=request.form['ctype']
            minusage=request.form['minusage']
            maxusage=request.form['maxusage']
            amount=request.form['amount']
            id=db.insert("insert into tariff values(null,'%s','%s','%s','%s')"%(types,minusage,maxusage,amount))
            if(id>0):
                print("ok")
                return redirect(url_for('adminviewtariff'))

            else:
                print("ok")
        return render_template('admin/adminaddtariff.html')
    else:
        return redirect(url_for('home'))

@app.route('/adminviewtariff')
def adminviewtariff():

    if not session.get("login_id") is None:
        if "delete" in request.args:
            id=request.args['id']
            print(id)
            res=db.delete("delete from tariff where tariff_id='%s'" %(id))
            return redirect(url_for('adminviewtariff'))
        res=db.select("select * from tariff")
        return render_template('admin/adminviewtariff.html',data=res)
    else:
        return redirect(url_for('home'))

    

@app.route('/admin_home')
def admin_home():
    if not session.get("login_id") is None:

        return render_template("admin/admin_home.html")
    else:
        return redirect(url_for('home'))
@app.route('/employee_home')
def employee_home():
    if not session.get("login_id") is None:

        return render_template('employee/employee_home.html')
    else:
        return redirect(url_for('home'))
    
@app.route('/logout',methods=['get','post'])
def logout():
    session.clear()
    return redirect(url_for('home'))

# app.run(debug=True,host="192.168.1.5",port=5007)
app.run(debug=True,port=5013,host='')