import React, { useState } from 'react'
import useApiRequest from './hooks/useApiRequest'
import BookList from './components/BookList';
import BookForms from './components/BookForms';
import UpdateBook from './components/UpdateBook';

export const Book = () => {
  const [books, setBooks] = useState([{}]);
  const {getAllData, getDataById, postData, updateData, deleteData} = useApiRequest(books, null);
  const [toggleModal, setToggleModal] = useState(false);
  const [toggleEdit, setToggleEdit] = useState(false);
  const [bookToUpdate, setBookToUpdate] = useState({});
  const getAllBooks = async () => {
      const resp = await getAllData("/books").then(
        data => {
          console.log(data);
          setBooks(data);
        }
      );
  }
  const updateBook = async (book) =>  {
    const resp = await updateData("/books",book).then(
      data => {
          console.log(data);
          // setBooks(data);
      }
    )
  }
  const createBook = () => {
    setToggleModal(true);
  }
  const addNewBook = (bookData) => {
    const updatedBookList = [...books, bookData]
    setBooks(updatedBookList);
  } 
  const displayBookEdit = (bookId) => {
    setToggleEdit(true)
    const book = books.filter((book) => book.bookId === bookId)
    setBookToUpdate(book);
  }
  const updateNewBook = (updatedBook) => {
    // const toUpdateBook = books.filter((book) => book.bookId !== updatedBook.bookId)
    // const updatedBookList = [...toUpdateBook, updateBook];
    // setBooks(updatedBookList)
  }
  return (
    <div>
        <button onClick={() => getAllBooks()}>Get all books</button>
        <button onClick={() => createBook(1)}>Create new book</button>
        <BookList bookList={books} setBooks={setBooks} displayBookEdit={displayBookEdit}/>
        <BookForms 
          toggleModal={toggleModal}
          setToggleModal={setToggleModal}
          addBook={addNewBook}
        />
        {
          toggleEdit ? 
          <UpdateBook
            toggleModal={toggleEdit}
            setToggleModal={setToggleEdit}
            bookDataInit={bookToUpdate}
            updateNewBook={updateNewBook}
          /> : <></>
        }
 
    </div>
  )
}
