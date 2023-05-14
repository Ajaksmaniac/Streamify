import React, { FormEvent, useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import { uploadVideo } from '../../util/videoUtil';
import { STATUS_CODES } from 'http';

const AddVideoModal = (props:{channelId:number, accessToken:string}) => {

    // Show messages if video name already exists



    const [show, setShow] = useState(false);

    const [name,setName] = useState("");
    const [description,setDescription] = useState("");
    const [file,setFile] = useState("" as any);


    const handleChange = (e:any) =>{
        if(e.target.name == 'name') setName(e.target.value)
        if(e.target.name == 'description') setDescription(e.target.value)
        if (e.target.name === "file") {
            setFile(e.target.files[0]);
          }    }
    const handleSubmit = (e: any) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("file", file);
        formData.append("name", name);
        formData.append("description", description);
        formData.append("channelId", props.channelId.toString());
      
        uploadVideo(formData, props.accessToken).then(res=>{
            if(res.status == 200){
                window.location.reload()
            }
        })
      };
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    return (
        <>
        <Button variant="primary" onClick={handleShow}>
        Add new Video
      </Button>
        <Modal
            show={show}
            onHide={handleClose}
        >
            <Form onSubmit={(e) => handleSubmit(e)}>
              <Modal.Header closeButton>
          <Modal.Title>Modal title</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          
            <Form.Group className="mb-3" controlId="name">
            <Form.Label>Video name</Form.Label>
                <Form.Control type="text" placeholder="Enter Video Name"
                    name='name'
                    value={name} 
                    onChange={(e)=>handleChange(e)}/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="description">
            <Form.Label>Video Description</Form.Label>
                <Form.Control type="text" placeholder="Enter Video Description"
                    name='description'
                    value={description} 
                    onChange={(e)=>handleChange(e)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="file">
            <Form.Label>Upload video file</Form.Label>
                <Form.Control 
                    type="file"
                    size="lg" 
                    onChange={(e)=>handleChange(e)}
                    name='file'
                    />
                    
            </Form.Group>
          
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" type="submit">Add Video</Button>
        </Modal.Footer>
        </Form>
        </Modal>
        </>
    );
};

export default AddVideoModal;