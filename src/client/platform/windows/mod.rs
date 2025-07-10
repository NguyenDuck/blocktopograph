/**
 * Copyright © 2025 NguyenDuck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
////////////////////////////////////////////////////////////////////////
use super::PlatformAPI;
use raw_window_handle::HasWindowHandle;
use winit::application::ApplicationHandler;
use winit::event::WindowEvent;

#[derive(Debug, Default)]
pub struct WindowsAppHandler {
    window: Option<winit::window::Window>,
    attributes: winit::window::WindowAttributes,
}

pub struct WindowsPlatform;

impl PlatformAPI for WindowsPlatform {
    fn create_window(
        title: &str,
    ) -> Result<raw_window_handle::RawWindowHandle, super::PlatformError> {
        let event_loop = winit::event_loop::EventLoop::new().unwrap();

        let mut handler = WindowsAppHandler::default();

        handler.attributes = winit::window::WindowAttributes::default()
            .with_blur(true)
            .with_title(title);

        event_loop.run_app(&mut handler).unwrap();

        let window_handle = handler.window.unwrap().window_handle().unwrap().as_raw();

        Ok(window_handle)
    }

    fn get_time_ms(self) -> u64 {
        unimplemented!()
    }

    fn poll_events(&mut self) -> Vec<super::PlatformEvent> {
        unimplemented!()
    }
}

impl ApplicationHandler for WindowsAppHandler {
    fn resumed(&mut self, event_loop: &winit::event_loop::ActiveEventLoop) {
        let window = event_loop.create_window(self.attributes.clone()).unwrap();
        self.window = Some(window);
    }

    fn window_event(
        &mut self,
        event_loop: &winit::event_loop::ActiveEventLoop,
        _: winit::window::WindowId,
        event: WindowEvent,
    ) {
        match event {
            WindowEvent::CloseRequested => event_loop.exit(),
            _ => {}
        }
    }
}
