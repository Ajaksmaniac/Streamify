import { Navbar, Col } from "react-bootstrap";



export default function Footer() {
    let fullYear = new Date().getFullYear();
  return(
    <Navbar bg="dark" variant="dark" className="navbar fixed-bottom">
        <Col lg={12} className="text-center text-muted">
            <div>{fullYear} - {fullYear+1} All Rights Reserved by Aleksandar Jovanov</div>
        </Col>
    </Navbar>
  )
}