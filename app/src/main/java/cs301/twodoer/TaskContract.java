package cs301.twodoer;

import android.provider.BaseColumns;

//Defines the variables used to access the data in the database
/*Works with the SQLite database that comes built into Android Studio  (Used Aldo Ziflaj's "Starting Android Development, Creating a Todo App" tutorial at http://www.sitepoint.com/starting-android-development-creating-todo-app/ to create this)*/
public class TaskContract {
    public static final String DB_NAME = "com.example.TodoList.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TASK = "task";
        public static final String PRIORITY = "priority"; //Column which will be used to store task priority in Version 2
        public static final String _ID = BaseColumns._ID;
    }
}

