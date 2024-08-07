import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import VideoPage from '../VideoPage';
import { useAuth } from '../../hooks/useAuth';
import { getVideoDetailsById } from '../../util/videoUtil';
import { getChannelDetails } from '../../util/channelUtil';
import { getCommentsForVideo } from '../../util/commentUtil';
import { Channel, Comment, Video } from '../../constants/types';
import CommentBox from '../../components/CommentBox';

jest.mock('../../hooks/useAuth');
jest.mock('../../util/videoUtil');
jest.mock('../../util/channelUtil');
jest.mock('../../util/commentUtil');
jest.mock('../../components/CommentBox', () => () => <div>CommentBox</div>);
jest.mock('../../components/AddComment', () => () => <div>AddComment</div>);
jest.mock('../../components/DescriptionBox', () => () => <div>DescriptionBox</div>);
jest.mock('../../components/alerts/CommentDeletedAlert', () => () => <div>CommentDeletedAlert</div>);
jest.mock('../../components/buttons/DeleteVideoButton', () => () => <button>DeleteVideoButton</button>);


describe('VideoPage Component', () => {
  const mockVideo: Video = {
    id: 1,
    name: 'Test Video',
    channelId: 1,
    url: '/test-video-url',
    description: 'Test description'
  };

  const mockChannel: Channel = {
    id: 1,
    channelName: 'Test Channel',
    username: 'testuser'
  };

  const mockComments: Comment[] = [
    { id: 1, content: 'Test comment 1', videoId: 1, userId: 1, commented_at: new Date() },
    { id: 2, content: 'Test comment 2', videoId: 1, userId: 2, commented_at: new Date() }
  ];

  beforeEach(() => {
    (useAuth as jest.Mock).mockReturnValue({
      user: jest.fn().mockReturnValue({
        username: 'testuser',
        role: { id: 1, name: 'admin' },
        subscribedChannels: [],
        id: 1
        
      }),
    });

    (getVideoDetailsById as jest.Mock).mockResolvedValue({ data: mockVideo });
    (getChannelDetails as jest.Mock).mockResolvedValue({ data: mockChannel });
    (getCommentsForVideo as jest.Mock).mockResolvedValue({ data: mockComments });
  });

  test('renders video details, channel details, and comments', async() => {

    render(
      <MemoryRouter initialEntries={['/video/1']} initialIndex={0}>
        <VideoPage />
      </MemoryRouter>,
    );
    expect(screen.getByText('DeleteVideoButton')).toBeInTheDocument();
    expect(screen.getByText('CommentDeletedAlert')).toBeInTheDocument();
    await waitFor(() => {
      expect(screen.getByText('Test Video')).toBeInTheDocument()
      expect(screen.getByText('Test Channel')).toBeInTheDocument()
      expect(screen.getAllByText('CommentBox')).toHaveLength(2)
      expect(screen.getByText('AddComment')).toBeInTheDocument()
      expect(screen.getByText('DescriptionBox')).toBeInTheDocument()
    });

   
  });

  test('displays error messages correctly', async () => {
    (getVideoDetailsById as jest.Mock).mockRejectedValueOnce(new Error('Error fetching video'));
    (getChannelDetails as jest.Mock).mockRejectedValueOnce(new Error('Error fetching channel'));
    (getCommentsForVideo as jest.Mock).mockRejectedValueOnce(new Error('Error fetching comments'));

    render(
      <MemoryRouter initialEntries={['/video/1']}>

         <VideoPage />
         </MemoryRouter>
    );

    await waitFor(() => expect(screen.queryByText('Test Video')).not.toBeInTheDocument());
    await waitFor(() => expect(screen.queryByText('Test Channel')).not.toBeInTheDocument());
    await waitFor(() => expect(screen.queryByText('CommentBox')).not.toBeInTheDocument());
  });
});
