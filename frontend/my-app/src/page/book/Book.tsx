import React, { useContext, useEffect } from 'react'
import { useState } from 'react';
import axios from '../../axios/axios';
import { Table, Button, Container, Nav } from 'react-bootstrap';
import { AuthContext } from '../../context/AuthProvider';
import BookModal from './BookModal';
import { useNavigate } from 'react-router-dom';
import NavBar from './NavBar';

import "./book.css"

type BooksType = {
    bookId : number
    name : string,
    author : string
}[]
type BookType = {
    bookId : number
    name : string,
    author : string
}
const bookInit = [{
    bookId : -1,
    name : "",
    author : ""
}]

const Book = () => {
    const [books,setBooks] = useState<BooksType>(bookInit);
    const { getJwt  } = useContext(AuthContext);
    const [modal,setModal] = useState<boolean>(false);
    const [trigger, setTrigger] = useState<boolean>(false);
    const [isEdit, setIsEdit] = useState<boolean>(false);
    const [bookId, setBookId] = useState<number>(-2);

    useEffect(() => {
        const jwt = getJwt();
        const headers = {
            "Authorization" : `Bearer ${jwt}`,
            "Content-Type" : "application/json"
        } 
        axios.get("/book", {
                headers : headers
            }).then((res) => {
                console.log("SIEMA")
                setBooks(res.data);
            })
            .catch((err) => {
                console.log(err)
            })
    },[trigger])

    const handleOnEdit = (book : BookType) => {
        setBookId(book.bookId)
        setIsEdit(true)
        setModal(true);
    }

    const triggerReRender = () => {
        setTimeout(() => {
            setTrigger(!trigger)
        },1)
    }

    const handleOnDelete = (book : BookType) => {
        const deleteBook = async () => {
            console.log(book);
            const jwt = getJwt();
            const headers = {
                "Authorization" : `Bearer ${jwt}`,
                "Content-Type" : "application/json"
            } 
            await axios.delete(`/book/${book.bookId}`, { headers : headers})
                .catch((err) => {
                    console.log(err)
                })
            triggerReRender()
        }
        deleteBook()
    }

    return (
        <div className="libr-container">
            <NavBar/>
            <table className='book-table'>
                <thead>
                    <th>Author</th>
                    <th>Name</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </thead>
                <tbody>
                    {
                        books.map((book : BookType) => {
                            return (
                                <tr key={book.bookId + book.author} >
                                    <td key={book.bookId} className='gray'>{book.name}</td>
                                    <td key={book.bookId} className='white'>{book.author}</td>
                                    <td key={book.bookId} className='gray'>
                                        <button onClick={() => handleOnEdit(book)} className="tbl-btn">Edit</button>
                                    </td>
                                    <td key={book.bookId} className='white'>
                                        <button onClick={() => handleOnDelete(book)} className='tbl-btn'>Delete</button>
                                    </td>
                                </tr>
                            )
                        })
                    }
                </tbody>
            </table>
            <button onClick={() => setModal(true)} className='create-book'>Create new</button>
            <BookModal 
                isShown={modal}
                handleModalHide={() => setModal(false)} 
                triggerReRender={triggerReRender}
                setIsEdit={() => setIsEdit(false)}
                isEdit={isEdit}
                bookId={bookId}
            /> 
        </div>
    )
}

export default Book