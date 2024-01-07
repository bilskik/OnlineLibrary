import React, { useContext } from 'react'
import { Modal, Form, Button, Stack } from 'react-bootstrap'
import { useState } from "react";
import axios from '../../axios/axios';
import { AuthContext } from '../../context/AuthProvider';
import "./bookmodal.css"

type bookType = {
    name : string,
    author : string
}
const initBook = {
    name : "",
    author : ""
}
type BookModalType = {
    isShown : boolean
    handleModalHide : () => void
    triggerReRender : () => void
    isEdit : boolean
    setIsEdit : () => void
    bookId : number
}
const BookModal = ({ isShown , handleModalHide, triggerReRender, isEdit, setIsEdit, bookId } : BookModalType) => {
    const [book, setBook] = useState<bookType>(initBook);
    const { getJwt } = useContext(AuthContext);
    const handleHide = () => {
        handleModalHide()
    }
    const handleOnBookAdd = () => {
        const postData = async() => {
            const jwt = getJwt();
            console.log(getJwt())
            const headers = {
                "Authorization" : `Bearer ${jwt}`,
                "Content-Type" : "application/json"
            } 
            await axios.post("/book", book, {
                headers : headers
            }).then(() => {
                setBook(initBook)
                triggerReRender()
                handleModalHide()
            }).catch((err) => {
                console.log(err)
            })

        }
        const putData = async () => {
            const jwt = getJwt();
            console.log(getJwt())
            const headers = {
                "Authorization" : `Bearer ${jwt}`,
                "Content-Type" : "application/json"
            } 
            const bookToPut : any = { ...book }
            bookToPut.bookId= bookId
            await axios.put("/book", bookToPut, {
                headers : headers
                }).then(() => {

                }).catch((err) => {
                    console.log(err)
                })
            triggerReRender()
            setIsEdit()
            handleModalHide()
        }
        if(isEdit) {
            putData()
        } else {
            postData()
        }

    }
    const handleOnExit = () => {
        handleModalHide()
    }
    return (
        <Modal show={isShown} onHide={handleHide} className='modal-container'>
            <Modal.Header>
                Post Book
            </Modal.Header>
            <Form className='modal-form'>
                <Form.Group className='form-group'>
                    <Form.Label>
                        Name
                    </Form.Label>
                    <Form.Control
                        id="book"
                        type="text"
                        placeholder='Enter book name'
                        value={book.name}
                        onChange={(e : React.ChangeEvent<HTMLInputElement>) => setBook({...book, name : e.target.value})}
                    />
                </Form.Group>
                <Form.Group className='form-group'>
                    <Form.Label>
                        Author
                    </Form.Label>
                    <Form.Control
                        id="author"
                        type="text"
                        placeholder='Enter author name'
                        value={book.author}
                        onChange={(e : React.ChangeEvent<HTMLInputElement>) => setBook({...book, author : e.target.value})}
                    />
                </Form.Group>
            </Form>
            <Stack direction='horizontal' gap={2} className='justify-content-center'>
                <button onClick={handleOnBookAdd} className='btn-modal'>
                    Submit
                </button>
                <button onClick={handleOnExit} className='btn-modal'>
                    Cancel
                </button>
            </Stack>
        </Modal>
    )
}

export default BookModal