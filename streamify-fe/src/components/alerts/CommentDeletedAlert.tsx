import React, { useState } from "react";
import { Alert, Button, Col, Row } from "react-bootstrap";

const CommentDeletedAlert = (props: { show: boolean; content: string }) => {
  const [show, setShow] = useState(true);
  return (
    <Alert show={show} variant="danger">
      <Row>
        <Col>
          <p>{props.content}</p>
        </Col>
        <Col>
          <Button
            onClick={() => setShow(false)}
            variant="outline-danger"
            className="w-1 h-1"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              className="bi bi-x"
              viewBox="0 0 16 16"
            >
              <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"></path>
            </svg>
          </Button>
        </Col>
      </Row>
    </Alert>
  );
};

export default CommentDeletedAlert;
