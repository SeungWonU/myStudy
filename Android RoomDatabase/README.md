# Room

기존에는 SQLite3 라이브러리를 사용해 로컬 데이터베이스를 사용했다.

## Room

- SQLite를 쉽게 사용할 수 있는 DB 객체 매핑 라이브러리 SQL
- Query를 컴파일 시 검증가능(Select * from table A where -) → 실행전에 오류를 수정함
- LiveData를 사용하여 DB가 변경될 때마다 쉽게 UI를 변경가능

## Room의 3요소

- @Database : 클래스를 DB로 지정하는 annotation(RoomDatabase를 상속받음)
- @Entity : 클래스를 테이블 스키마로 지정하는 annotation
- @Dao : 클래스를 DAO(Data Access Object)로 지정하는 annotation(Query 문)

## Gradle 설정

```kotlin
apply plugin: 'kotlin-kapt'

dependencies{
	def room_version = "2.2.5"
	implementation "androidx.room:room-runtime:$room_version"
	kapt "androidx.room:room-compiler:$room_version"
	// optional - Kotlin Extensions and Coroutines support for Room
	implementation "androidx.room:room-ktx:$room_version"
	// optional - Test helpers
	testImplementation "androidx.room:room-testing:$room_version"
}
```

---

## Entity 생성

- data class 사용하면 편리함  → (copy,hash,eqauls)를 자동으로 만들어주기 때문에 나중을 위해
- class 이름은 보통 테이블 이름(바꿔줄 수 있음 tableName = ? )
- ColumnInfo로 이름을 재지정 가능
- @PrimaryKey(autoGenerate = true)하면 키를 자동으로 생성

ex) CREATE TABLE student_table(student_id INTEGER PRIMARY KEY,name TEXT NOT NULL)

```kotlin
@Entity(tableName = "student_table") 
data class Student(
@PrimaryKey @ColumnInfo(name = "student_id")
val id : Int,	//null이 불가능한 타입
val name : String // null 가능한 타입은 ? 붙여야 됨
)
```

- 중요!! → val 을 안 적을 경우 data class의 속성이나 테이블의 컬럼이 될 수 없다 !!(일반 생성자 인자가 됨)  class 안에 추가 정의하고 싶으면  data class Student(){//대괄호 안에 메소드 정의}

ex) primaryKey와 foreignKey 사용 예제

```kotlin
@Entity(tableName = "enrollment",
		primaryKeys = ["sid, "cid"],
		foreignKeys = [
				ForeignKey(entity = Student::class,parentColumns = ["student_id"],childColumns = ["sid"]),
	      ForeginKey(entity = ClassInfo::class,parentColumns = ["id"], childColumns = ["cid"])
		]
)
data class Enrollment(
	val sid : Int,
	val cid : Int,
	val grade : String? = null
)
```

---

## DAO 생성

- Interface 나 abstract class 정의(실제로 만드는게 아니라 껍데기만, 실제 메소드의 구현은 Room Complier가 알아서 만들어 줌)
- @Insert,@Update,@Delete,@Query
- primary key를 통해 변경or 삭제함

- key 중복(onConflict 지정 ! )

```kotlin
OnConflictStrategy.ABORT : key 충돌시 종료
OnConflictStrategy.IGNORE : key 충돌 무시
OnConflictStrategy.REPLACE : key 충돌시 데이터 변경
```

- Query
    - @Query로 리턴되는 데이터 타입을 LiveData<>로 하면 Observer를 통해 데이터 업데이트 함

```kotlin
@Query("SELECT * FROM student_table WHERE name = :sname") //매개변수 sname을 :sname 사용
supspend fun getStudentByName(sname:String): List<Student>
```

- suspend는 coroutine을 사용한다. 이 메소드를 부를 때 runBlocking{}내에서 호출해야 함

     → 시간이 좀 걸리는 함수이기 때문(Block 가능성이 있는)

     → 반드시 비동기적으로 동작하도록 해야 함 

     → 원래는 별도의 쓰레드에서 사용해야 함(병행 수행하면 됨) 하지만 coroutine은 메인쓰레드 하나로도 병행수행 가능!( 스위칭되는 시점을 runBlocking{}안에서 호출해야됨)

     → suspend만 사용하면 함수가 바로 실행되는 것이 아니라 나중에 coroutine으로 불려서 처리됨

    ex) runBlocking{ getStudentByName}

    - LiveData는 비동기로 동작하기 때문에 coroutine을 사용할 필요 없다

```kotlin
@Dao
interface MyDAO{
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertStudent(student:Student)
	
	@Query("SELECT * FROM student_table") //LiveData 사용
	fun getAllStudents(): LiveData<List<Student>>

	@Query("SELECT * FROM student_table WHERE name = :sname") //coroutine 사용
	suspend fun getStudentByName(sname: String): List<Student>

	@Delete //primary key로 구분
	suspend fun deleteStudent(student: Student)

  @Transaction // 1:N관계 , select문이 두 개를 하나의 Transcation으로 묶음
  @Query("SELECT * FROM student_table WHERE student_id = :id")
	suspend fun getStudentsWithEnrollment(id: Int): List<StudentWithEnrollments>	
}
```

## Database 생성

- RoomDatabase를 상속하여 자신의 Room 클래스를 생성
- 버전 관리!!( Database annotation에 지정)
    - migration 수행 (ex> version 1 → 2)
- DAO를 가져오는 getter 메소드 생성( 실제 메소드는 자동 생성)
- 싱글턴 패턴 사용( DB객체는 리소스를 많이 차지함으로 인스턴스는 앱 전체에서 하나만 있으면된다. 매번 생성할 필요 x)
- Room 객체 생성은 Room.databaseBuilder()를 이용(빌더패턴)

```kotlin
//@Database에 반드시 entity들이 있어야 함, 버전 번호도 포함
@Database(entities = [Student::class, ClassInfo::class, Enrollment::class],version=1)
abstract class MyDatabase : RoomDatabase(){
	abstract fun getMyDao() : MyDAO // Room compiler가 알아서 해줌
	
	companion object{ // companion은 static과 유사
		private var INSTANCE: MyDatabase? = null
		private val MIGRATION_1_2 = object : Migration(1,2){
			ovveride fun migrate(database: SupportSQLiteDatabase){}
		}
		fun getDatabase(context:Context):MyDatabase{
			if(INSTANCE ==null){//Singleton 패턴
					INSTANCE = Room.databaseBuilder(
						context, MyDatabase::class.java, "school_database")
						.addMigrations(MIGRATION_1_2)
						.build()
			}	
			return INSTANCE as MyDatabase
		}
}
```

## Migration

- MyRoomDatabase 객체 생성 후 addMigrations()메소드를 호출하여 Migration 지정

```kotlin
private val Migration_1_2 = object : Migration(1,2){
	oveeride fun migrate(database : SupportSQLiteDatabase){
		database.execSQL("ALTER TABLE student_table ADD COLUMN last_update INTEGER")
	}
}
```

## LiveData

- 데이터가 변경될 때마다 자동으로 Observer의 onChanged()가 호출됨

```kotlin
val allStudents = myDao.getAllStudents()
allStudents.observe(this){ //변경이 될때마다 람다함수가 호출이 됨(SAM)
	val str = StringBuilder().apply{
		for((id,name) in it){ //it은 LiveData<List<Student>> 가 넘어옴
			append(id)
			append("-")
			append(name)
			append("\n")	
		}	
	}.toString()
binding.textStudentList.text = str // textView에 변경될 때마다 바뀜
}
```

[7주차-룸 - Google Drive](https://drive.google.com/drive/folders/1AVJIwCQvfP9Brpt3pP-vxTYV1NkVY80u)

Repository ViewModel 아키텍쳐

[](https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0)