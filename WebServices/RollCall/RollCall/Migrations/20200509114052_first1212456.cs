using Microsoft.EntityFrameworkCore.Migrations;

namespace RollCall.Migrations
{
    public partial class first1212456 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Leave_File_AttachmentFileId",
                table: "Leave");

            migrationBuilder.DropIndex(
                name: "IX_Leave_AttachmentFileId",
                table: "Leave");

            migrationBuilder.DropPrimaryKey(
                name: "PK_File",
                table: "File");

            migrationBuilder.DropColumn(
                name: "AttachmentFileId",
                table: "Leave");

            migrationBuilder.DropColumn(
                name: "FileId",
                table: "File");

            migrationBuilder.DropColumn(
                name: "FileName",
                table: "File");

            migrationBuilder.DropColumn(
                name: "FileType",
                table: "File");

            migrationBuilder.AddColumn<int>(
                name: "Id",
                table: "File",
                nullable: false,
                defaultValue: 0)
                .Annotation("SqlServer:Identity", "1, 1");

            migrationBuilder.AddColumn<int>(
                name: "LeaveId",
                table: "File",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Name",
                table: "File",
                maxLength: 255,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Type",
                table: "File",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddPrimaryKey(
                name: "PK_File",
                table: "File",
                column: "Id");

            migrationBuilder.CreateIndex(
                name: "IX_File_LeaveId",
                table: "File",
                column: "LeaveId");

            migrationBuilder.AddForeignKey(
                name: "FK_File_Leave_LeaveId",
                table: "File",
                column: "LeaveId",
                principalTable: "Leave",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_File_Leave_LeaveId",
                table: "File");

            migrationBuilder.DropPrimaryKey(
                name: "PK_File",
                table: "File");

            migrationBuilder.DropIndex(
                name: "IX_File_LeaveId",
                table: "File");

            migrationBuilder.DropColumn(
                name: "Id",
                table: "File");

            migrationBuilder.DropColumn(
                name: "LeaveId",
                table: "File");

            migrationBuilder.DropColumn(
                name: "Name",
                table: "File");

            migrationBuilder.DropColumn(
                name: "Type",
                table: "File");

            migrationBuilder.AddColumn<int>(
                name: "AttachmentFileId",
                table: "Leave",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "FileId",
                table: "File",
                type: "int",
                nullable: false,
                defaultValue: 0)
                .Annotation("SqlServer:Identity", "1, 1");

            migrationBuilder.AddColumn<string>(
                name: "FileName",
                table: "File",
                type: "nvarchar(255)",
                maxLength: 255,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "FileType",
                table: "File",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddPrimaryKey(
                name: "PK_File",
                table: "File",
                column: "FileId");

            migrationBuilder.CreateIndex(
                name: "IX_Leave_AttachmentFileId",
                table: "Leave",
                column: "AttachmentFileId");

            migrationBuilder.AddForeignKey(
                name: "FK_Leave_File_AttachmentFileId",
                table: "Leave",
                column: "AttachmentFileId",
                principalTable: "File",
                principalColumn: "FileId",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
