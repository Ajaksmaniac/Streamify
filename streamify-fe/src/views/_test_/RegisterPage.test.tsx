import { render, screen, fireEvent } from '@testing-library/react';
import RegisterPage from '../../views/RegisterPage';

describe('RegisterPage Component', () => {
  test('renders registration form', () => {
    render(<RegisterPage />);
    expect(screen.getByPlaceholderText(/username/i)).toBeInTheDocument();
    expect(screen.getByTestId("pass")).toBeInTheDocument();
    expect(screen.getByTestId("confirmPass")).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /register/i })).toBeInTheDocument();
  });

  test('handles form input changes', () => {
    render(<RegisterPage />);

    fireEvent.change(screen.getByLabelText(/username/i), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByTestId("pass"), { target: { value: 'testpass' } });
    fireEvent.change(screen.getByTestId("confirmPass"), { target: { value: 'testpass' } });

    expect(screen.getByLabelText(/username/i)).toHaveValue('testuser');
    expect(screen.getByTestId("pass")).toHaveValue('testpass');
    expect(screen.getByTestId("confirmPass")).toHaveValue('testpass');
  });

  test('shows error message when passwords do not match', () => {
    render(<RegisterPage />);

    fireEvent.change(screen.getByLabelText(/username/i), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByTestId("pass"), { target: { value: 'testpass' } });

    fireEvent.change(screen.getByTestId("confirmPass"), { target: { value: 'differentpass' } });

    fireEvent.click(screen.getByRole('button', { name: /register/i }));
    expect(screen.getByText(/passwords do not match/i)).toBeInTheDocument();
  });

  test('shows error message when fields are empty and form is submitted', () => {
    render(<RegisterPage />);

    fireEvent.click(screen.getByRole('button', { name: /register/i }));

    expect(screen.getByText(/fill out all the fields/i)).toBeInTheDocument();
  });

  test('hides error messages when all fields are correctly filled', () => {
    render(<RegisterPage />);

    fireEvent.change(screen.getByLabelText(/username/i), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByTestId("pass"), { target: { value: 'testpass' } });
    fireEvent.change(screen.getByTestId("confirmPass"), { target: { value: 'testpass' } });

    fireEvent.click(screen.getByRole('button', { name: /register/i }));

    expect(screen.queryByText(/passwords do not match/i)).toBeNull();
    expect(screen.queryByText(/fill out all the fields/i)).toBeNull();
  });
});
