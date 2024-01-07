import React, { useContext } from 'react'
import { Modal, Form, Button, Stack } from 'react-bootstrap'
import { useState } from "react";
import axios from '../../axios/axios';
import { AuthContext } from '../../context/AuthProvider';
import "./bookmodal.css"
import { resolveData } from '../../service/resolveData';
import { UserSettingsContext } from '../../context/UserSettingsProvider';

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
    const { lang, theme } = useContext(UserSettingsContext);
    const data = resolveData(lang);

    const handleHide = () => {
        handleModalHide()
    }

    const handleOnBookAdd = () => {
        const postData = async() => {
            const jwt = getJwt();
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
                {
                    isEdit ? ( data.titleModalEditBook ) : ( data.titleModalPostBook ) 
                }
            </Modal.Header>
            <Form className='modal-form'>
                <Form.Group className='form-group'>
                    <Form.Label>
                        { data.name }
                    </Form.Label>
                    <Form.Control
                        id="book"
                        type="text"
                        placeholder={ data.modalNamePlaceholder }
                        value={book.name}
                        onChange={(e : React.ChangeEvent<HTMLInputElement>) => setBook({...book, name : e.target.value})}
                    />
                </Form.Group>
                <Form.Group className='form-group'>
                    <Form.Label>
                        { data.author }
                    </Form.Label>
                    <Form.Control
                        id="author"
                        type="text"
                        placeholder={ data.modalAuthorPlaceholder }
                        value={book.author}
                        onChange={(e : React.ChangeEvent<HTMLInputElement>) => setBook({...book, author : e.target.value})}
                    />
                </Form.Group>
            </Form>
            <Stack direction='horizontal' gap={2} className='justify-content-center'>
                <button onClick={handleOnBookAdd} className='btn-modal'>
                    { data.submitModal }
                </button>
                <button onClick={handleOnExit} className='btn-modal'>
                    { data.cancelModal }
                </button>
            </Stack>
        </Modal>
    )
}

export default BookModal