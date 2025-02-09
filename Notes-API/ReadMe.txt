add  a note
{
  "title": "My New Note Title",
  "content": "This is the content of my new note.  It can be quite long."
}
Read all
[
  {
    "title": "My New Note Title",
    "content": "This is the content of my new note.  It can be quite long."
  },
  {
    "title": "Another Note",
    "content": "More content here..."
  }
  // ... more notes
]

3 Read a note
GET /api/notes/1
4
{
  "title": "Updated Note Title",
  "content": "This is the updated content."
}

5

{
  "title": "Partially Updated Title" // Only the title is being updated
}

6
DELETE /api/notes/1