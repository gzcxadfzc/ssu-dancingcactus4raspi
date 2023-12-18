import yt_dlp
import sys

yt_dlp.postprocessor.FFmpegPostProcessor._ffmpeg_location.set(
    R'C:/Users/yongs/ffmpeg-2023-11-20-git-e56d91f8a8-full_build/bin')
EXT = 'wav'
OUTPUT_DIRECTORY = 'C:/Users/yongs/Desktop/SpringProject/CactusSpeaker4Pi/src/main/resources/musics/'

def extract(video_id):
    URLS = [video_id]
    ydl_opts = {
        'format': EXT + '/bestaudio/best',
        # ℹ️ See help(yt_dlp.postprocessor) for a list of available Postprocessors and their arguments
        'postprocessors': [{  # Extract audio using ffmpeg
            'key': 'FFmpegExtractAudio',
            'preferredcodec': EXT,
        }],
        'outtmpl': OUTPUT_DIRECTORY + '%(title)s.%(ext)s',
    }
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        error_code = ydl.download(URLS)
        print(error_code)

url = sys.argv[1]
# url = '30TkClWvT5k'
extract(url)
