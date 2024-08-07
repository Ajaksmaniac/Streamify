import { render, screen } from '@testing-library/react';

import NotFoundPage from '../NotFoundPage';

test('fetches and displays videos when none found', () => {

    render(<NotFoundPage />);

    expect(screen.getByText(/NOT FOUND/i)).toBeInTheDocument();
});
