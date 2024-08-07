import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import LoginPage from '../../views/LoginPage';
// import { axiosMockAdapterInstance } from '../../__mocks__/axios';
import {loginWithUsernameAndPassword} from '../../util/authUtil';
jest.mock('../../util/authUtil')
describe('Login Component', () => {

  afterEach(() => {
    // cleaning up the mess left behind the previous test
    // mockAxios.reset();
    // loginWithUsernameAndPassword.
    });

  test('renders login form', () => {
    render(<LoginPage />);
    expect(screen.getByLabelText(/username/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /login/i })).toBeInTheDocument();
  });

  test('handles form input changes', () => {
    render(<LoginPage />);

    fireEvent.change(screen.getByLabelText(/username/i), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByLabelText(/password/i), { target: { value: 'testpass' } });

    expect(screen.getByLabelText(/username/i)).toHaveValue('testuser');
    expect(screen.getByLabelText(/password/i)).toHaveValue('testpass');
  });


  test('handles on submit button', () => {
    loginWithUsernameAndPassword.mockResolvedValueOnce({status: 200})
    render(<LoginPage />);



    fireEvent.change(screen.getByLabelText(/username/i), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByLabelText(/password/i), { target: { value: 'testpass' } });

    fireEvent.click(screen.getByRole('button', { name: /login/i }));
// let login  = jest.fn();
// // loginWithUsernameAndPassword()
//     expect(screen.getByLabelText(/username/i)).toNot
//     expect(screen.getByLabelText(/password/i)).toHaveValue('testpass');
    // axiosMockAdapterInstance
    expect(loginWithUsernameAndPassword).toHaveBeenCalledTimes(1);
  });
});
