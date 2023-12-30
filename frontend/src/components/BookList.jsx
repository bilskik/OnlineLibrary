import React, { useState, useEffect } from 'react'
import "../css/table.css";
import axios from '../axios/axios';

const BookList = ({ bookList, setBooks, displayBookEdit }) => {
    const [slice, setSlice] = useState(bookList.length > 5 ? 5 : bookList.length);
    useEffect(() => {
        if(bookList.length > slice && bookList.length < 6) {
            setSlice(bookList.length)
        }
    },[bookList])
    const deleteBookById = async (id ) => {
        const headers =  { 'Content-Type' : 'application/json'}
        try {
            const response = await axios.delete(`/books/${id}`, { headers })
            .then((data) => {
                return data;
            })
            const filteredBookList = bookList.filter((book) => book.bookId !== id);
            setBooks(filteredBookList);
        } catch(err) {
            console.log(err)
        } 
      }
    const updateBook = (bookId) => {
        displayBookEdit(bookId)
    }
    const showMore = () => {
        if(slice + 5 < bookList.length) {
            setSlice(slice + 5)
        }
    }
    return (
        <>
            <table id='table'>
                <thead className='thead'>
                    <th>Name</th>
                    <th>Author</th>
                    <th>Price</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </thead>
                <tbody className='table__body'>
                    { 
                        bookList.slice(0,slice).map((book) => {
                            return (
                            <tr className='rows'>
                                <td className='text'>{book.name}</td>
                                <td className='text'>{book.author}</td>
                                <td className='text'>{book.price}</td>
                                { 
                                    book.name ? 
                                    <>
                                        <td>
                                            <button onClick={() => updateBook(book.bookId)}>Update book</button>
                                        </td>
                                        <td>
                                            <button onClick={() => deleteBookById(book.bookId)}>Delete book</button>
                                        </td>
                                    </> : <></>
                                }
                               
                            </tr>
                            )
                        }) 
                    }
                </tbody>
            </table>
            <button onClick={() => showMore()}>Show more</button>
        </>
    )
}

export default BookList