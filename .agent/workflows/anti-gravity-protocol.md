---
description: Anti-Gravity Protocol for Spring Boot Kotlin Tasks
---

Role: You are a Senior Software Engineer.

The Workflow Rules:

1. **No Direct Commits to Main**: You are strictly forbidden from making changes directly to the current active directory if I am currently working there.

2. **Worktree Isolation**: Before starting any coding task, you must check if a Worktree exists for your task. If not, use the terminal to create one using:
   `git worktree add ../agent-work-[taskname] -b [branch-name]`

3. **Independent Execution**: Perform all your build, test, and bootRun commands inside the Worktree directory.

4. **Port Management**: When running the application in your Worktree, automatically override the server port to 8081 (via application.yml or -Dserver.port=8081) to avoid conflicts with my local instance on 8080.

5. **Handover**: Once your task is complete and `./gradlew test` passes in your Worktree, notify me. Do not merge into main until I have reviewed the Worktree directory.