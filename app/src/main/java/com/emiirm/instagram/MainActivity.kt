package com.emiirm.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var auth: FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth=FirebaseAuth.getInstance()

        fun createUserId(email: String, password: String){
            auth?.createUserWithEmailAndPassword(email, password)
                    ?. addOnCompleteListener(this){task->
                        if(task.isSuccessful()){
                            //아이디 생성이 완료 되었을 때
                            val user=auth?.getCurrentUser()
                        }else{
                            Toast.makeText(this, "아이디 생성 실패",Toast.LENGTH_SHORT).show()

                        }
                    }
        }//createUserId
        fun verifyEmail(){
            auth?.currentUser?.sendEmailVerification()
                    ?.addOnCompleteListener { task->
                        if(task.isSuccessful()){
                            Toast.makeText(this, "유효청 체크 됨",Toast.LENGTH_LONG).show()
                        }
                    }
        }//verifyEmail
        fun updatePassword(newPassword: String) {
            auth?.currentUser?.updatePassword(newPassword)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful())
                            Toast.makeText(this, "패스워드 변경 성공", Toast.LENGTH_LONG).show()
                    }
        }//updatePassword
    }
    fun updateEmail(newEmail: String){
        auth?.currentUser?. updateEmail(newEmail)
                ?.addOnCompleteListener { task->
                    Toast.makeText(this, "이메일 변경 성공",Toast.LENGTH_LONG).show()
                }
    }//updateEmail
    fun deleteId(){
        auth?.currentUser?. delete()
                ?.addOnCompleteListener { task->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "회원 아이디 삭제 성공", Toast.LENGTH_LONG).show()
                    }
                }//updateEmai
        fun reauthenticate(email: String, password: String){
            val credential=EmailAuthProvider
                    .getCredential(email, password)

            auth?.currentUser?. reauthenticate(credential)
                    ?.addOnCompleteListener{task: Task<Void>->
                        if(task.isComplete){
                            Toast.makeText(this, "재인증 성공",Toast.LENGTH_LONG).show()
                        }
                    }
        }//requthenticate
        var authListener: FirebaseAuth.AuthStateListener?=null
        authListener=FirebaseAuth.AuthStateListener { FirebaseAuth->
            val user=FirebaseAuth.currentUser
            if(user!=null){
                Toast.makeText(this, "로그인 됨", Toast.LENGTH_LONG).show()
        }else{
                Toast.makeText(this, "로그인 알 됨",Toast.LENGTH_LONG).show()
            }
    }
        fun onStart() {
            super.onStart()
            auth?.addAuthStateListener { authListener!! }
        }
        fun onPause(){
            super.onPause()
            auth?.removeAuthStateListener { authListener!! }
        }
    }
}
