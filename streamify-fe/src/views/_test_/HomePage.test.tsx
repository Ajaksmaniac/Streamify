import { render, screen, waitFor } from '@testing-library/react';
import {HomePage} from '../../views/HomePage';
import { Video } from '../../constants/types';
import { getAllVideos } from '../../util/videoUtil';
import { MemoryRouter } from 'react-router-dom';

jest.mock('../../util/videoUtil')
const mockVideos:any = {
    data:[
        { id: 1, name: "Video 1", channelId: 1, url: "some Url", description: "desc" },
        { id: 2, name: "Video 2", channelId: 1, url: "some Url", description: "desc" },
        
    ] as Video[]
}

describe('Home Page Component', () => {

    test('fetches and displays videos when none found', async () => {
        (getAllVideos as jest.Mock).mockResolvedValue(mockVideos)

        render(
            <MemoryRouter>
                <HomePage />
            </MemoryRouter>
        );

        expect(screen.getByText(/Recommended videos/i)).toBeInTheDocument();
        expect(getAllVideos).toHaveBeenCalledTimes(1); // Adjust the URL as necessary    expect(screen.getByText(/2 items/i)).toBeInTheDocument();
        await waitFor(() => {
            expect(screen.getByText(/Video 1/i)).toBeInTheDocument();
            expect(screen.getByText(/Video 2/i)).toBeInTheDocument();

        });

    });
});
