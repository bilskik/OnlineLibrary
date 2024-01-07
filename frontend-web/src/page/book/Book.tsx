import React, { useContext, useEffect } from 'react'
import { useState } from 'react';
import axios from '../../axios/axios';
import { Table, Button, Container, Nav } from 'react-bootstrap';
import { AuthContext } from '../../context/AuthProvider';
import BookModal from './BookModal';
import NavBar from './NavBar';

import "./book.css"
import { UserSettingsContext } from '../../context/UserSettingsProvider';
import { resolveData } from '../../service/resolveData';

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
    const [books,setBooks] = useState<BooksType | undefined>();
    const { getJwt  } = useContext(AuthContext);
    const [modal,setModal] = useState<boolean>(false);
    const [trigger, setTrigger] = useState<boolean>(false);
    const [isEdit, setIsEdit] = useState<boolean>(false);
    const [bookId, setBookId] = useState<number>(-2);
    const { lang, theme } = useContext(UserSettingsContext);
    const data = resolveData(lang);

    useEffect(() => {
        const jwt = getJwt();
        const headers = {
            "Authorization" : `Bearer ${jwt}`,
            "Content-Type" : "application/json"
        } 
        axios.get("/book", {
                headers : headers
            }).then((res) => {
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
        <div 
            className="libr-container" 
            style={{ backgroundColor : theme === "light" ? "#f8f9fd" : "#9c9c9c"}}
        >
            <NavBar/>
            <table className='book-table'>
                <thead>
                    <th>{ data?.author }</th>
                    <th>{ data?.name }</th>
                    <th>{ data?.edit }</th>
                    <th>{ data?.delete }</th>
                </thead>
                <tbody>
                    {
                        books?.map((book : BookType) => {
                            return (
                                <tr key={book.bookId + book.author} >
                                    <td className='gray'>{book.name}</td>
                                    <td className='white'>{book.author}</td>
                                    <td className='gray'>
                                        <button onClick={() => handleOnEdit(book)} className="tbl-btn">{ data.edit }</button>
                                    </td>
                                    <td className='white'>
                                        <button onClick={() => handleOnDelete(book)} className='tbl-btn'>{ data.delete }</button>
                                    </td>
                                </tr>
                            )
                        })
                    }
                </tbody>
            </table>
            <button onClick={() => setModal(true)} className='create-book'>{ data.createNew }</button>
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