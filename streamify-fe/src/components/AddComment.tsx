import { useState } from "react";
import { Alert, Button, Col, Container, Form, Row } from "react-bootstrap";
import { useAuth } from "../hooks/useAuth";
import { addCommentForVideo } from "../util/commentUtil";
import CommentPostedAlert from "./alerts/CommentPostedAlert";

const AddComment = (props: {videoId:number, callback: ()=>void}) => {
    const auth = useAuth();
    const [commentContent, setCommentContent] = useState('');
    const [showCommentAlert, setShowCommentAlert] = useState(false);

    const handleChange = (e: any) =>{
        setCommentContent(e.target.value)
    }
    const onSubmit = (e: any) =>{
        e.preventDefault()
        if(commentContent.length > 2){
            addCommentForVideo(props.videoId,commentContent,auth.user()?.accessToken!).then(()=>{
                setShowCommentAlert(true)
                props.callback()
            })
            // window.location.reload();
        }
    }
  return (
    <>
   
    {showCommentAlert && (
           <CommentPostedAlert content="Comment Successfully posted" show={showCommentAlert}/> 

    )}

    
    <Container className="w-100 mb-5">
      <Form onSubmit={onSubmit}>
        <Row>
            <Col>
            <Form.Group>
          
          <Form.Control
            className="d-inline"
            type="comment"
            placeholder="Write Comment"
            name="comment"
            value={commentContent}
            onChange={(e) => handleChange(e)}
            required
            data-test-id={"commentField"}
          />
        </Form.Group>
            </Col>
            <Col>
            <Button variant="primary" type="submit" className="d-inline"   data-test-id={"commentButton"}>
          Add Comment
        </Button>
            </Col>
        </Row>
       
       
      </Form>
    </Container>
    </>
  );
};

export default AddComment;
