package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activities.OrganizationDataList
import com.example.myapplication.adaptor.OrganizationAdaptor
import com.example.myapplication.models.OrganizationModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//fetching activity
class fetching : AppCompatActivity() {

    private lateinit var OrganizationRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var  OrganizationList : ArrayList<OrganizationModel>
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        OrganizationRecyclerView=findViewById(R.id.rvOrganization)
        OrganizationRecyclerView.layoutManager= LinearLayoutManager(this)
        OrganizationRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.loadingData)

        OrganizationList = arrayListOf<OrganizationModel>()

        getBillsData()
    }

    //get bill data
    private fun getBillsData() {
        OrganizationRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef= FirebaseDatabase.getInstance().getReference("Organization")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                OrganizationList.clear()
                if(snapshot.exists()){
                    for(organizationsnap in snapshot.children){
                        val organizationData = organizationsnap.getValue(OrganizationModel::class.java)
                        OrganizationList.add(organizationData!!)
                    }
                    val mAdaptor = OrganizationAdaptor(OrganizationList)
                    OrganizationRecyclerView.adapter=mAdaptor

                    mAdaptor.setOnItemClickListner(object :OrganizationAdaptor.onItemClickListner{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@fetching, OrganizationDataList::class.java)

                            //put extras
                            intent.putExtra("organizationId",OrganizationList[position].orggId)
                            intent.putExtra("organizationName",OrganizationList[position].orgName)
                            intent.putExtra("organizationId",OrganizationList[position].orgId)
                            intent.putExtra("organizationEmail",OrganizationList[position].orgEmail)
                            startActivity(intent)
                        }
                    })

                    OrganizationRecyclerView.visibility=View.VISIBLE
                    tvLoadingData.visibility=View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}