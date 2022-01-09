package com.example.healthlog.adapter

import com.example.healthlog.adapter.DoctorAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthlog.ui.doctor.DoctorViewModel
import android.widget.LinearLayout
import android.widget.EditText
import android.os.Bundle
import com.example.healthlog.R
import android.text.TextWatcher
import android.text.Editable
import android.view.View.OnTouchListener
import com.example.healthlog.model.Doctor
import android.content.Intent
import com.example.healthlog.DoctorActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.healthlog.HealthLog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import com.example.healthlog.ui.hospital.HospitalViewModel
import com.example.healthlog.adapter.SuspectedPatientAdapter
import android.widget.TextView
import com.example.healthlog.model.SuspectedPatient
import com.example.healthlog.handler.HospitalProfileHandler
import com.example.healthlog.model.Hospital
import android.content.DialogInterface
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.CollectionReference
import com.example.healthlog.handler.NewPatientHandler
import com.example.healthlog.handler.HospitalHandler
import com.example.healthlog.ui.dashboard.DashboardViewModel
import com.example.healthlog.adapter.DashboardAdapter
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.healthlog.handler.PatientViewHandler
import com.example.healthlog.model.Patient
import android.widget.ArrayAdapter
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView
import com.google.firebase.firestore.GeoPoint
import com.example.healthlog.adapter.DoctorAdapter.DoctorViewHolder
import com.example.healthlog.adapter.DashboardAdapter.DashboardViewHolder
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnSuccessListener
import android.content.ContentValues
import com.google.android.gms.tasks.OnFailureListener
import com.example.healthlog.adapter.SuspectedPatientAdapter.SuspectedPatientViewHolder
import android.graphics.drawable.ColorDrawable
import androidx.core.app.NotificationManagerCompat
import android.os.Build
import androidx.annotation.RequiresApi
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import com.example.healthlog.MainActivity
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import kotlin.Throws
import com.example.healthlog.interfaces.DialogClickListener
import android.widget.RadioGroup
import android.widget.RadioButton
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.example.healthlog.LoginActivity
import com.example.healthlog.AboutActivity
import com.example.healthlog.SettingsActivity
import com.example.healthlog.handler.PatientLogHandler
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.healthlog.SettingsActivity.SettingsFragment
import androidx.preference.PreferenceFragmentCompat
import android.util.DisplayMetrics
import android.view.*
import com.example.healthlog.interfaces.OnItemClickListener
import org.junit.runner.RunWith

// COMPLETED(SHANK) implement suspectedPatientAdapter using @SuspectedPatient model
class SuspectedPatientAdapter(private val suspectedPatientList: MutableList<SuspectedPatient?>?) :
    RecyclerView.Adapter<SuspectedPatientViewHolder?>() {
    var listener: OnItemClickListener<*>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuspectedPatientViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.suspected_patient_list_item, parent, false)
        return SuspectedPatientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SuspectedPatientViewHolder, position: Int) {
        val suspectedPatient = suspectedPatientList.get(position)
        holder.suspectName.setText(suspectedPatient.getName())
        holder.suspectAge.setText(suspectedPatient.getAge())
        holder.suspectMobile.setText(suspectedPatient.getContact())
        holder.bind(suspectedPatient, listener)
    }

    fun add(suspectedPatient: SuspectedPatient?) {
        suspectedPatientList.add(suspectedPatient)
        notifyDataSetChanged()
    }

    fun setListener(listener: OnItemClickListener<*>?) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return suspectedPatientList.size
    }

    inner class SuspectedPatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var suspectName: TextView?
        var suspectAge: TextView?
        var suspectMobile: TextView?
        var view: View?
        fun bind(
            suspectedPatient: SuspectedPatient?, listener: OnItemClickListener<*>?
        ) {
            view.setOnClickListener(
                View.OnClickListener { view -> listener.onItemClicked(suspectedPatient, view) })
        }

        init {
            view = itemView
            suspectName = itemView.findViewById<TextView?>(R.id.suspect_list_item_name_textView)
            suspectAge = itemView.findViewById<TextView?>(R.id.suspect_list_item_age_textView)
            suspectMobile = itemView.findViewById<TextView?>(R.id.suspect_list_item_mobile_textView)
        }
    }
}