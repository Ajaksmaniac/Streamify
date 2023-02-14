import { Card, Col, Container } from "react-bootstrap";
import { VideoBoxProps } from "../constants/types";

export default function VideoBox(props: VideoBoxProps) {
  return (
    <Col xs={6} md={4} lg={2} className="mb-4">
      <Card>
        <Card.Body>
        <h3>{props.video.title}</h3>
          <p>{props.video.author}</p></Card.Body> 
         
        
      </Card>
    </Col>
  );
}
