package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.dao.AppDatabase
import com.example.todolist.data_model.ToDoItem
import com.example.todolist.ui.theme.ToDoListTheme

var items = mutableListOf(
    ToDoItem(1, "name1", "description1", "date1", "Completed"),
    ToDoItem(2, "name2", "description2", "date2", "In Progress"),
    ToDoItem(3, "name2", "description2", "date2", "To Do"),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
                    val x = database.toDoCardDao()
                    items = x.getAll().toMutableList()
                    MainContent()

//                    val db = Room.databaseBuilder(
//                        applicationContext,
//                        AppDatabase::class.java, "Sample.db"
//                    )
//                        .createFromAsset("database/database.db")
//                        .build()
//                    val userDao = db.toDoCardDao()
//                    val toDoCards: List<ToDoCard> = userDao.getAll()

                }
            }
        }
    }
}

@Composable
fun ToDoItemCard(item: ToDoItem, onDeleteClick: (Int) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )  {
//
//        Text("${item.name}")
//        Text("${item.description}")
//        Text("${item.date}")
//        Text("${item.state}")
//        IconButton(onClick = { onDeleteClick(item.id) }) {
//            Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
//        }
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "${item.name}" /*, style = MaterialTheme.typography.h6 */)
                IconButton(onClick = {onDeleteClick(item.id)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }

            Text(text = "${item.description}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(text = "${item.date}", color = Color.White)
                }

                Text(
                    text = "${item.status}",
                    color = when (item.status) {
                        "In Progress" -> Color.Green
                        "Completed" -> Color.Blue
                        else -> Color.Gray
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun ToDoItemCardPreview() {
    ToDoItemCard(ToDoItem(1, "name1", "description1", "24.11.2023", "In Progress"), onDeleteClick = {})
}

@Composable
fun MainContent() {
    var cards by remember { mutableStateOf(items) }

    ToDoListTheme {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ){
            items(cards) { card ->
                ToDoItemCard(card, onDeleteClick = { cardId -> cards = cards.filterNot { it.id == cardId }.toMutableList() } )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 200)
@Composable
fun MainContentPreview() {
    MainContent()
}
