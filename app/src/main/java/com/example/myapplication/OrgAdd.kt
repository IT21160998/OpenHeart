package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Date


//Add organization class
class OrgAdd : AppCompatActivity() {
    private lateinit var orgName: EditText
    private lateinit var orgId: EditText
    private lateinit var orgemail: EditText
    private lateinit var addOrg:Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_org_add)

        orgName = findViewById(R.id.OrganizationName)
        orgId = findViewById(R.id.organizationID)
        orgemail = findViewById(R.id.Email)
        addOrg = findViewById(R.id.addOrg)

        dbRef= FirebaseDatabase.getInstance().getReference("Organization")

        addOrg.setOnClickListener{
            saveOrgData()
        }
    }
    private fun saveOrgData(){
        val OrgName =  orgName.text.toString()
        val OrgId = orgId.text.toString()
        val OrgEmail = orgemail.text.toString()

        //validation
        if(OrgName.isEmpty()){
            OrgName.error = "Please enter Organization name"
        } else if(OrgId.isEmpty()){
            OrgId.error = "Please enter Organization ID"
        }else if(OrgId.isEmpty()){
            OrgEmail.error ="Please enter Email"
        } else {

            val orId = dbRef.push().key!!

            val org = OrganizationModel(orId,,billAmount,billDate,billComment)

            //data inserted
            dbRef.child(orId).setValue(Organization)
                .addOnCompleteListener {
                    Toast.makeText(this,"Data inserted Successfully",Toast.LENGTH_LONG
                    ).show()

                    OrgName.text.clear()
                    OrgId.text.clear()
                    OrgEmail.text.clear()
                    OrgPassword.text.clear()

                }.addOnFailureListener{err ->
                    Toast.makeText(this,"Error $err.get",Toast.LENGTH_LONG).show()
                }
        }

    }
}