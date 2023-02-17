import { Card, Col } from "react-bootstrap";
import { VideoBoxProps } from "../constants/types";
import { useNavigate } from "react-router-dom";

export default function VideoBox(props: VideoBoxProps) {
  const navigate = useNavigate();

  return (
    <Col xs={6} md={4} lg={2} className="mb-4"
    onClick={() => navigate(`/video/${props.video.id}`, { state: { video: props.video } })}>
       <Card>
        <Card.Body>
          <h3>{props.video.name}</h3>
          <p>{props.video.postedBy}</p>
        </Card.Body> 
      </Card>
    </Col>
  );
}
