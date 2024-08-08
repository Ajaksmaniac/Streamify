import { render, screen } from '@testing-library/react';
import BoxGrid from '../BoxGrid';
import axios from 'axios';
import { Video } from '../../constants/types';
jest.mock('react-router-dom');

test('fetches and displays videos when none found', () => {
    const mockVideos: Video[] = [
        { id: 1, name: "Video 1", channelId: 1, url: "some Url", description: "desc" },
        { id: 2, name: "Video 2", channelId: 2, url: "some Url2", description: "desc2" },
    ];
      
    render(<BoxGrid items={mockVideos}/>);

    mockVideos.forEach(video => {
      expect(screen.getByText(video.name)).toBeInTheDocument();
    });
});
